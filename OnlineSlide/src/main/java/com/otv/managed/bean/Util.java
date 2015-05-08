package com.otv.managed.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.otv.model.CustomElement;

public class Util {

	public static List<CustomElement> getElements(Element impress) throws IOException{
		List<CustomElement> list = new ArrayList<CustomElement>();
		
		//File input = new File("D:/calismalar/htmlslide/HtmlParser/orijinal.html");
//		File input = new File(path);
//		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
//				
//		Element impress = doc.getElementById("impress");
		Elements elements = impress.children();
		System.out.println("size="+elements.size());
		
		
		
		
		int i=0;
		Iterator it = elements.iterator();
		while(it.hasNext()) {
			Element element = (Element)it.next();
	         //System.out.print(element + " ");
			System.out.println(i++);
	        CustomElement customElement = new CustomElement();
	        customElement.setElement(element);
			list.add(customElement);
	      }
		return list;
	}
	
	public static void main(String[] args) throws Exception{
		//getElements("C:/Users/002834/Desktop/OTV_JSF_Spring_Hibernate/OTV_JSF_Spring_Hibernate/src/main/webapp/Demo/neo4jVisulation.html");
	}
	
	public static void removeElement(Element impress,Element element){
		//Elements elements = impress.children();
		element.remove();
		Iterator it = impress.children().iterator();
		int index=0;
		while(it.hasNext()) {
			Element elementTemp = (Element)it.next();
			String id = elementTemp.attr("id");
			
			if(id.equals(element.attr("id"))){
				impress.children().remove(index);
		
				break;
			}
			index++;
			}
	}
	
	public static void resetFile(String source,String destination) throws Exception{
		File sourceFile = new File(source);
		File destinationFile = new File(destination);

		Document doc = Jsoup.parse(sourceFile, "UTF-8", "http://example.com/");
		
		FileOutputStream fileOutputStream = new FileOutputStream(destinationFile);
		fileOutputStream.write(doc.ownerDocument().html().getBytes());
		
		
		fileOutputStream.close();
	}
}
