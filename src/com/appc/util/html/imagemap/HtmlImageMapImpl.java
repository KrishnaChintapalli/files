package com.appc.util.html.imagemap;

import com.appc.util.html.exception.RenderException;
import com.appc.util.html.renderer.LayoutHolder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xhtmlrenderer.layout.Styleable;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.render.Box;
import org.xhtmlrenderer.render.InlineLayoutBox;
import org.xhtmlrenderer.render.LineBox;

public class HtmlImageMapImpl implements HtmlImageMap {
	private static Set<String> searchedAttributes = stringSet(
			new String[] { "href", "onclick", "ondblclick", "onmousedown", "onmouseup" });
	private static Set<String> allowedAttributes = stringSet(new String[] { "href", "target", "title", "class",
			"tabindex", "dir", "lang", "accesskey", "onblur", "onclick", "ondblclick", "onfocus", "onmousedown",
			"onmousemove", "onmouseout", "onmouseover", "onmouseup", "onkeydown", "onkeypress", "onkeyup" });
	private LayoutHolder layoutHolder;

	public HtmlImageMapImpl(LayoutHolder layoutHolder) {
		this.layoutHolder = layoutHolder;
	}

	public String getImageMap(String mapName, String imageURL) {
		StringWriter writer = new StringWriter();
		saveImageMap(writer, mapName, imageURL);
		return writer.toString();
	}

	public void saveImageMap(Writer writer, String mapName, String imageURL) {
		try {
			writer.append("<map name=\"").append(mapName).append("\">\n");
			for ( Collection<ElementBox> boxes : getClickableBoxes().values()) {
				for (ElementBox elementBox :boxes ) {
					int x1 = elementBox.getLeft();
					int y1 = elementBox.getTop();
					int x2 = elementBox.getRight();
					int y2 = elementBox.getBottom();
					writer.append(String.format("<area coords=\"%s,%s,%s,%s\" shape=\"rect\"", new Object[] {
							Integer.valueOf(x1), Integer.valueOf(y1), Integer.valueOf(x2), Integer.valueOf(y2) }));
					NamedNodeMap attributes = elementBox.getElement().getAttributes();
					int i = 0;
					for (int l = attributes.getLength(); i < l; ++i) {
						Node node = attributes.item(i);
						String name = node.getNodeName();
						String value = node.getNodeValue();
						if ((name != null) && (value != null)) {
							String lowerName = name.toLowerCase();
							if (allowedAttributes.contains(lowerName)) {
								writer.append(" ").append(lowerName).append("=\"").append(value.replace("\"", "&quot;"))
										.append("\"");
							}
						}
					}
					writer.append(">\n");
				}
			}
			writer.append("</map>\n");
		} catch (IOException e) {
			throw new RenderException("IOException while writing client-side image map.", e);
		}
	}

	public String getImageMapDocument(String imageURL) {
		StringWriter writer = new StringWriter();
		saveImageMapDocument(writer, imageURL, true);
		return writer.toString();
	}

	public void saveImageMapDocument(Writer writer, String imageURL, boolean closeWriter) {
		try {
			writer.append(
					"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n");
			writer.append("<html>\n<head><style>area {cursor: pointer;}</style></head>\n");
			writer.append("<body style=\"margin: 0; padding: 0; text-align: center;\">\n");
			saveImageMap(writer, "map", imageURL);
			writer.append("<img border=\"0\" usemap=\"#map\" src=\"");
			writer.append(imageURL);
			writer.append("\"/>\n");
			writer.append("</body>\n</html>");
		} catch (IOException e) {
		} finally {
			if (closeWriter)
				try {
					writer.close();
				} catch (IOException ignore) {
				}
		}
	}

	public void saveImageMapDocument(File file, String imageURL) {
		try {
			saveImageMapDocument(new FileWriter(file), imageURL, true);
		} catch (IOException e) {
			throw new RenderException(String.format("IOException while writing image map document '%s'.",
					new Object[] { file.getAbsolutePath() }), e);
		}
	}

	public void saveImageMapDocument(String filename, String imageURL) {
		saveImageMapDocument(new File(filename), imageURL);
	}

	public Map<Element, Collection<ElementBox>> getClickableBoxes() {
		Box rootBox = this.layoutHolder.getRootBox();
		HashMap boxes = new HashMap();
		addClickableElements(rootBox, boxes, new HashSet());
		return boxes;
	}

	private void addClickableElements(Styleable styleable, HashMap<Element, Collection<ElementBox>> boxes,
			Set<Styleable> visited) {
		if ((styleable == null) || (visited.contains(styleable))) {
			return;
		}
		visited.add(styleable);

		addIfClickable(styleable, boxes);
		Iterator i$;
		if (styleable instanceof Box)
//			for (Styleable child : ((Box) styleable).getChildren())
//				addClickableElements(child, boxes, visited);
			for (i$ = ((Box) styleable).getChildren().iterator(); i$.hasNext();) {
				Object child = i$.next();
				if (child instanceof Styleable)
					addClickableElements((Styleable) child, boxes, visited);
			}
		
		if (styleable instanceof InlineLayoutBox) {
			for (i$ = ((InlineLayoutBox) styleable).getInlineChildren().iterator(); i$.hasNext();) {
				Object child = i$.next();
				if (child instanceof Styleable)
					addClickableElements((Styleable) child, boxes, visited);
			}
		} else if (styleable instanceof BlockBox) {
			for (i$ = ((BlockBox) styleable).getInlineContent().iterator(); i$.hasNext();) {
				Object child = i$.next();
				if (child instanceof Styleable)
					addClickableElements((Styleable) child, boxes, visited);
			}
//			List content = ((BlockBox) styleable).getInlineContent();
//			if (content != null) {
//				for (Styleable child : content.iterator())
//					addClickableElements(child, boxes, visited);
//			}
		}
		else if (styleable instanceof LineBox) {
//			for (Styleable child : ((LineBox) styleable).getNonFlowContent())
//				addClickableElements(child, boxes, visited);
			for (i$ = ((LineBox) styleable).getNonFlowContent().iterator(); i$.hasNext();) {
				Object child = i$.next();
				if (child instanceof Styleable)
					addClickableElements((Styleable) child, boxes, visited);
			}
		}
	}

	private void addIfClickable(Styleable styleable, HashMap<Element, Collection<ElementBox>> boxes) {
		Element clickable = getClickableElement(styleable);
		if (clickable == null) {
			return;
		}
		ElementBox elementBox = createElementBox(styleable, clickable);
		if ((elementBox == null) || (elementBox.isEmpty())) {
			return;
		}
		Collection elementBoxes = (Collection) boxes.get(clickable);
		if (elementBoxes == null) {
			elementBoxes = new ArrayList();
			boxes.put(clickable, elementBoxes);
			elementBoxes.add(elementBox);
			return;
		}
		if (!(elementBox.containedIn(elementBoxes)))
			elementBoxes.add(elementBox);
	}

	private ElementBox createElementBox(Styleable styleable, Element element) {
		if (styleable instanceof InlineLayoutBox) {
			InlineLayoutBox box = (InlineLayoutBox) styleable;
			int width = Math.max(box.getInlineWidth(), box.getWidth());
			return new ElementBox(element, box.getAbsX(), box.getAbsY(), width, box.getHeight());
		}
		if (styleable instanceof Box) {
			Box box = (Box) styleable;
			return new ElementBox(element, box.getAbsX(), box.getAbsY(), box.getWidth(), box.getHeight());
		}
		return null;
	}

	private Element getClickableElement(Styleable box) {
		Element element = box.getElement();
		while (element != null) {
			if (isClickable(element)) {
				return element;
			}
			Node parentNode = element.getParentNode();
			element = (parentNode instanceof Element) ? (Element) parentNode : null;
		}
		return null;
	}

	private boolean isClickable(Element element) {
		for (String attribute : searchedAttributes) {
			String value = element.getAttribute(attribute);
			if (StringUtils.isNotBlank(value)) {
				return true;
			}
		}
		return false;
	}

	private static HashSet<String> stringSet(String[] items) {
		return new HashSet(Arrays.asList(items));
	}
}