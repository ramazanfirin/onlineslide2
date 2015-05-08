package com.otv.model;

import org.jsoup.nodes.Element;

public class CustomElement{

	public String x;
	public String y;
	public String z;
	public String scale;
	public String rotate;
	
	
	public void setX(String x) {
		if("".equals(x))
			x="0";
		this.x = x;
		element.attr("data-x", x);
	}

	public void setY(String y) {
		if("".equals(y))
			y="0";
		this.y = y;
		element.attr("data-y", y);
	}

	public void setZ(String z) {
		if("".equals(z))
			z="0";
		this.z = z;
		element.attr("data-z", z);
	}

	public void setScale(String scale) {
		if("".equals(scale))
			scale="1";
		this.scale = scale;
		element.attr("data-scale", scale);
	}

	public void setRotate(String rotate) {
		if("".equals(rotate))
			rotate="0";
		this.rotate = rotate;
		element.attr("data-rotate", rotate);
		
	}

	public CustomElement() {
		super();
		//setElement(new Element("", ""));
	}

	public Element element;
	
	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	
	public String getHtmlCode(){
		return element.html();
	}

	public String getId(){
		return element.attr("id");
	}
	
	public String getX(){
		if(element!=null)
			return element.attr("data-x");
		else
			return "";
	}
	
	public String getY(){
		if(element!=null)
			return element.attr("data-y");
		else
			return "";
	}
	
	public String getZ(){
		if(element!=null)	
		return element.attr("data-z");
		else
			return "";
	}
	
	public String getScale(){
		if(element!=null)
			return element.attr("data-scale");
		else
			return "";
	}
	
	public String getRotate(){
		if(element!=null)
			return element.attr("data-rotate");
		else
			return "";
	}
}
