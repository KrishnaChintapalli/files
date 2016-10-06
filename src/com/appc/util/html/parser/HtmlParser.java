/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appc.util.html.parser;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;

public abstract interface HtmlParser extends DocumentHolder {
	public abstract DOMParser getDomParser();

	public abstract void setDomParser(DOMParser paramDOMParser);

	public abstract void setDocument(Document paramDocument);

	public abstract void load(URL paramURL);

	public abstract void load(URI paramURI);

	public abstract void load(File paramFile);

	public abstract void load(Reader paramReader);

	public abstract void load(InputStream paramInputStream);

	public abstract void loadHtml(String paramString);

	public abstract void loadURI(String paramString);
}