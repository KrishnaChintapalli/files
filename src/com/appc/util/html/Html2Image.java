package com.appc.util.html;

import com.appc.util.html.imagemap.HtmlImageMap;
import com.appc.util.html.imagemap.HtmlImageMapImpl;
import com.appc.util.html.parser.HtmlParser;
import com.appc.util.html.parser.HtmlParserImpl;
import com.appc.util.html.renderer.ImageRenderer;
import com.appc.util.html.renderer.ImageRendererImpl;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import org.w3c.dom.Document;

public class Html2Image {
	private HtmlParser parser;
	private HtmlImageMap htmlImageMap;
	private ImageRenderer imageRenderer;

	public Html2Image() {
		this.parser = new HtmlParserImpl();
	}

	public HtmlParser getParser() {
		return this.parser;
	}

	public HtmlImageMap getHtmlImageMap() {
		if (this.htmlImageMap == null) {
			this.htmlImageMap = new HtmlImageMapImpl(getImageRenderer());
		}
		return this.htmlImageMap;
	}

	public ImageRenderer getImageRenderer() {
		if (this.imageRenderer == null) {
			this.imageRenderer = new ImageRendererImpl(this.parser);
		}
		return this.imageRenderer;
	}

	public static Html2Image fromDocument(Document document) {
		Html2Image html2Image = new Html2Image();
		html2Image.getParser().setDocument(document);
		return html2Image;
	}

	public static Html2Image fromHtml(String html) {
		Html2Image html2Image = new Html2Image();
		html2Image.getParser().loadHtml(html);
		return html2Image;
	}

	public static Html2Image fromURL(URL url) {
		Html2Image html2Image = new Html2Image();
		html2Image.getParser().load(url);
		return html2Image;
	}

	public static Html2Image fromURI(URI uri) {
		Html2Image html2Image = new Html2Image();
		html2Image.getParser().load(uri);
		return html2Image;
	}

	public static Html2Image fromFile(File file) {
		Html2Image html2Image = new Html2Image();
		html2Image.getParser().load(file);
		return html2Image;
	}

	public static Html2Image fromReader(Reader reader) {
		Html2Image html2Image = new Html2Image();
		html2Image.getParser().load(reader);
		return html2Image;
	}

	public static Html2Image fromInputStream(InputStream inputStream) {
		Html2Image html2Image = new Html2Image();
		html2Image.getParser().load(inputStream);
		return html2Image;
	}
}