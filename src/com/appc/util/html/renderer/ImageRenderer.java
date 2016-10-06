package com.appc.util.html.renderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;

public abstract interface ImageRenderer extends LayoutHolder {
	public abstract int getWidth();

	public abstract ImageRenderer setWidth(int paramInt);

	public abstract int getHeight();

	public abstract ImageRenderer setHeight(int paramInt);

	public abstract boolean isAutoHeight();

	public abstract ImageRenderer setAutoHeight(boolean paramBoolean);

	public abstract String getImageFormat();

	public abstract ImageRenderer setImageType(String paramString);

	public abstract BufferedImage getBufferedImage(int paramInt);

	public abstract BufferedImage getBufferedImage();

	public abstract void saveImage(OutputStream paramOutputStream, boolean paramBoolean);

	public abstract void saveImage(String paramString);

	public abstract void saveImage(File paramFile);

	public abstract ImageRendererImpl clearCache();

	public abstract float getWriteCompressionQuality();

	public abstract ImageRenderer setWriteCompressionQuality(float paramFloat);

	public abstract int getWriteCompressionMode();

	public abstract ImageRenderer setWriteCompressionMode(int paramInt);

	public abstract String getWriteCompressionType();

	public abstract ImageRenderer setWriteCompressionType(String paramString);
}