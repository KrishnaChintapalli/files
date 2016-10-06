package com.appc.util.html.imagemap;

import java.io.File;
import java.io.Writer;
import java.util.Collection;
import java.util.Map;
import org.w3c.dom.Element;

public abstract interface HtmlImageMap {
	public abstract Map<Element, Collection<ElementBox>> getClickableBoxes();

	public abstract String getImageMap(String paramString1, String paramString2);

	public abstract void saveImageMap(Writer paramWriter, String paramString1, String paramString2);

	public abstract String getImageMapDocument(String paramString);

	public abstract void saveImageMapDocument(String paramString1, String paramString2);

	public abstract void saveImageMapDocument(File paramFile, String paramString);

	public abstract void saveImageMapDocument(Writer paramWriter, String paramString, boolean paramBoolean);
}