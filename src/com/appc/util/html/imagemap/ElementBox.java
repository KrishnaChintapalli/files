package com.appc.util.html.imagemap;

import java.util.Collection;
import org.w3c.dom.Element;

public class ElementBox {
	private Element element;
	private int left;
	private int top;
	private int width;
	private int height;

	public ElementBox(Element element, int left, int top, int width, int height) {
		this.element = element;
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
	}

	public Element getElement() {
		return this.element;
	}

	public int getLeft() {
		return this.left;
	}

	public int getTop() {
		return this.top;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public int getRight() {
		return (this.left + this.width);
	}

	public int getBottom() {
		return (this.top + this.height);
	}

	public boolean isEmpty() {
		return ((this.width <= 0) || (this.height <= 0));
	}

	public boolean containedIn(Collection<ElementBox> elementBoxes) {
		for (ElementBox box : elementBoxes) {
			if (containedIn(box)) {
				return true;
			}
		}
		return false;
	}

	public boolean containedIn(ElementBox box) {
		return ((getTop() >= box.getTop()) && (getLeft() >= box.getTop()) && (getBottom() <= box.getBottom())
				&& (getRight() <= box.getRight()));
	}
}