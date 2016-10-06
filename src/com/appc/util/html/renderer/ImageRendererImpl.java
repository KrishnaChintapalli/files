/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appc.util.html.renderer;

import com.appc.util.html.exception.RenderException;
import com.appc.util.html.parser.DocumentHolder;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.w3c.dom.Document;
import org.xhtmlrenderer.render.Box;
import org.xhtmlrenderer.simple.Graphics2DRenderer;
import org.xhtmlrenderer.simple.XHTMLPanel;
import org.xhtmlrenderer.util.FSImageWriter;

public class ImageRendererImpl implements ImageRenderer {
	public static final int DEFAULT_WIDTH = 1024;
	public static final int DEFAULT_HEIGHT = 768;
	private DocumentHolder documentHolder;
	private int width = 1024;
	private int height = 768;
	private boolean autoHeight = true;

	private String imageFormat = null;
	private float writeCompressionQuality = 1.0F;
	private int writeCompressionMode = 3;
	private String writeCompressionType = null;
	private Box rootBox;
	private BufferedImage bufferedImage;
	private int cacheImageType = -1;
	private Document cacheDocument;

	public ImageRendererImpl(DocumentHolder documentHolder) {
		this.documentHolder = documentHolder;
	}

	public int getWidth() {
		return this.width;
	}

	public ImageRenderer setWidth(int width) {
		this.width = width;
		return this;
	}

	public int getHeight() {
		return this.height;
	}

	public ImageRenderer setHeight(int height) {
		this.height = height;
		return this;
	}

	public boolean isAutoHeight() {
		return this.autoHeight;
	}

	public ImageRenderer setAutoHeight(boolean autoHeight) {
		this.autoHeight = autoHeight;
		return this;
	}

	public String getImageFormat() {
		return this.imageFormat;
	}

	public ImageRenderer setImageType(String imageType) {
		this.imageFormat = imageType;
		return this;
	}

	public float getWriteCompressionQuality() {
		return this.writeCompressionQuality;
	}

	public ImageRenderer setWriteCompressionQuality(float writeCompressionQuality) {
		this.writeCompressionQuality = writeCompressionQuality;
		return this;
	}

	public int getWriteCompressionMode() {
		return this.writeCompressionMode;
	}

	public ImageRenderer setWriteCompressionMode(int writeCompressionMode) {
		this.writeCompressionMode = writeCompressionMode;
		return this;
	}

	public String getWriteCompressionType() {
		return this.writeCompressionType;
	}

	public ImageRenderer setWriteCompressionType(String writeCompressionType) {
		this.writeCompressionType = writeCompressionType;
		return this;
	}

	public BufferedImage getBufferedImage(int imageType) {
		Document document = this.documentHolder.getDocument();
		if ((this.bufferedImage != null) || (this.cacheImageType != imageType) || (this.cacheDocument != document)) {
			this.cacheImageType = imageType;
			this.cacheDocument = document;
			Graphics2DRenderer renderer = new Graphics2DRenderer();
			renderer.setDocument(document, document.getDocumentURI());
			Dimension dimension = new Dimension(this.width, this.height);
			this.bufferedImage = new BufferedImage(this.width, this.height, imageType);

			if (this.autoHeight) {
				Graphics2D graphics2D = (Graphics2D) this.bufferedImage.getGraphics();
				renderer.layout(graphics2D, new Dimension(this.width, this.height));
				graphics2D.dispose();

				Rectangle size = renderer.getMinimumSize();
				int autoWidth = (int) size.getWidth();
				int autoHeight = (int) size.getHeight();
				this.bufferedImage = new BufferedImage(autoWidth, autoHeight, imageType);
				dimension = new Dimension(autoWidth, autoHeight);
			}

			Graphics2D graphics2D = (Graphics2D) this.bufferedImage.getGraphics();
			renderer.layout(graphics2D, dimension);
			renderer.render(graphics2D);
			this.rootBox = renderer.getPanel().getRootBox();
			graphics2D.dispose();
		}
		return this.bufferedImage;
	}

	public Box getRootBox() {
		if (this.rootBox == null) {
			getBufferedImage();
		}
		return this.rootBox;
	}

	public ImageRendererImpl clearCache() {
		this.bufferedImage = null;
		this.rootBox = null;
		this.cacheDocument = null;
		this.cacheImageType = -1;
		return this;
	}

	public BufferedImage getBufferedImage() {
		return getBufferedImage(2);
	}

	public void saveImage(OutputStream outputStream, boolean closeStream) {
		save(outputStream, null, closeStream);
	}

	public void saveImage(File file) {
		try {
			BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
			save(outputStream, file.getName(), true);
		} catch (IOException e) {
			throw new RenderException("IOException while rendering image to " + file.getAbsolutePath(), e);
		}
	}

	public void saveImage(String filename) {
		saveImage(new File(filename));
	}

	private void save(OutputStream outputStream, String filename, boolean closeStream) {
		try {
			String imageFormat = getImageFormat(filename);
			FSImageWriter imageWriter = getImageWriter(imageFormat);
			boolean isBMP = "bmp".equalsIgnoreCase(imageFormat);
			BufferedImage bufferedImage = getBufferedImage((isBMP) ? 1 : 2);
			imageWriter.write(bufferedImage, outputStream);
		} catch (IOException e) {
		} finally {
			if (closeStream)
				try {
					outputStream.close();
				} catch (IOException ignore) {
				}
		}
	}

	private FSImageWriter getImageWriter(String imageFormat) {
		FSImageWriter imageWriter = new FSImageWriter(imageFormat);
		imageWriter.setWriteCompressionMode(this.writeCompressionMode);
		imageWriter.setWriteCompressionQuality(this.writeCompressionQuality);
		imageWriter.setWriteCompressionType(this.writeCompressionType);
		return imageWriter;
	}

	private String getImageFormat(String filename) {
		if (this.imageFormat != null) {
			return this.imageFormat;
		}
		if (filename != null) {
			return FormatNameUtil.formatForFilename(filename);
		}
		return FormatNameUtil.getDefaultFormat();
	}
}