package com.appc.util.html.parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import org.apache.xerces.parsers.DOMParser;
import org.cyberneko.html.HTMLConfiguration;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

public class HtmlParserImpl implements HtmlParser {
	private DOMParser domParser;
	private Document document;

	public HtmlParserImpl() {
		this.domParser = new DOMParser(new HTMLConfiguration());
		try {
			this.domParser.setProperty("http://cyberneko.org/html/properties/names/elems", "lower");
		} catch (SAXNotRecognizedException e) {
			throw new ParseException("Can't create HtmlParserImpl", e);
		} catch (SAXNotSupportedException e) {
			throw new ParseException("Can't create HtmlParserImpl", e);
		}
	}

	public DOMParser getDomParser() {
		return this.domParser;
	}

	public void setDomParser(DOMParser domParser) {
		this.domParser = domParser;
	}

	public Document getDocument() {
		return this.document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public void load(Reader reader) {
		try {
			this.domParser.parse(new InputSource(reader));
			this.document = this.domParser.getDocument();
		} catch (SAXException e) {
		} catch (IOException e) {
		} finally {
			try {
				reader.close();
			} catch (IOException ignore) {
			}
		}
	}

	public void load(InputStream inputStream) {
		try {
			this.domParser.parse(new InputSource(inputStream));
			this.document = this.domParser.getDocument();
		} catch (SAXException e) {
		} catch (IOException e) {
		} finally {
			try {
				inputStream.close();
			} catch (IOException ignore) {
			}
		}
	}

	public void loadURI(String uri) {
		try {
			InputSource inputSource = new InputSource(uri);
			inputSource.setEncoding("UTF-8");
			this.domParser.parse(inputSource);
			this.document = this.domParser.getDocument();
		} catch (SAXException e) {
			throw new ParseException(
					String.format("SAXException while parsing HTML from \"%s\".", new Object[] { uri }), e);
		} catch (IOException e) {
			throw new ParseException(
					String.format("SAXException while parsing HTML from \"%s\".", new Object[] { uri }), e);
		}
	}

	public void load(File file) {
		load(file.toURI());
	}

	public void load(URL url) {
		loadURI(url.toExternalForm());
	}

	public void load(URI uri) {
		loadURI(uri.toString());
	}

	public void loadHtml(String html) {
		load(new StringReader(html));
	}
}