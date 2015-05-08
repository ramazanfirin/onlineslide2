package com.otv.managed.bean;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;

import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.xslf.usermodel.DrawingParagraph;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFCommonSlideData;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.primefaces.model.DashboardModel;
import org.springframework.dao.DataAccessException;

import com.otv.model.CustomElement;
import com.otv.model.User;
import com.otv.user.service.IUserService;

/**
 * 
 * User Managed Bean
 * 
 * @author onlinetechvision.com
 * @since 25 Mar 2012
 * @version 1.0.0
 *
 */
@ManagedBean(name="htmlFileMB")
@SessionScoped
public class HtmlFileManagedBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final String SUCCESS = "success";
	private static final String ERROR   = "error";
	
	 private DashboardModel model;  
	 DataModel dataModel;
	 private String selectedId;
	 private CustomElement selectedElement = new CustomElement();
	 private Document doc;
	 private Element impress;
	
	 
	private String CONTENT_URL = "http://localhost:8080/external/impress.html";
	private String ORIGINAL_FILE = "http://localhost:8080/external/impressOrijinal.html";
	
	private static String WORKING_DIRECTORY="c:/OnlineSlide";
	private String selectedProject;
	
	private List<SelectItem> projectNames = new ArrayList<SelectItem>(); 
	public static void main(String[] args) {
		
	}
	
	public String getCONTENT_URL() {
		return FacesContext.getCurrentInstance().getCurrentInstance().getExternalContext().getRequestContextPath();
	}

	public String getRandomData(){
		Random randomGenerator = new Random();
		Long random = randomGenerator.nextLong();
		return String.valueOf(random) ;
	}
	
	public void setCONTENT_URL(String cONTENT_URL) {
		CONTENT_URL = cONTENT_URL;
	}

	public CustomElement getSelectedElement() {
		return selectedElement;
	}

	public void setSelectedElement(CustomElement selectedElement) {
		this.selectedElement = selectedElement;
	}

	public String getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
		System.out.println("bitti");
	}
	
	

	public HtmlFileManagedBean() throws Exception{
		super();
//		model = new DefaultDashboardModel();  
//        DashboardColumn column1 = new DefaultDashboardColumn();  
//        //column1.addWidget("slides"); 
//        model.addColumn(column1);
      
	}

	public DashboardModel getModel() {
		return model;
	}

	public void setModel(DashboardModel model) {
		this.model = model;
	}

	//Spring User Service is injected...
	@ManagedProperty(value="#{UserService}")
	IUserService userService;
	
	List<User> userList;
	
	private int id;
	private String name;
	private String surname;
	
	private List<CustomElement> elementList;
	private String editor;
	
	public String x;
	public String y;
	public String z;
	public String scale;
	public String rotate;
	
	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getZ() {
		return z;
	}

	public void setZ(String z) {
		this.z = z;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getRotate() {
		return rotate;
	}

	public void setRotate(String rotate) {
		this.rotate = rotate;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public List<CustomElement> getElementList() {
		
		return elementList;
	}

	public void setElementList(List<CustomElement> elementList) {
		this.elementList = elementList;
	}
	
	public void createNewProject(){
		File file = new File(WORKING_DIRECTORY+"/"+name);
		file.mkdir();
		
	}
	
	public String loadProject(){
		return "";
	}
	
	public void getValues(){
		try {
			  doc = Jsoup.parse(new File(getFile()), "UTF-8", "http://example.com/");
				impress = doc.getElementById("impress");
				System.out.println("size="+impress.children().size());
			elementList = Util.getElements(impress);
			//editor=elementList.get(0).toString();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", "Data Loaded"));  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public String selectSlide(){
		System.out.println("selectSlide="+getSelectedId());
		editor = selectedElement.getHtmlCode();selectedElement.getX();
		x=selectedElement.getX();
		y=selectedElement.getY();
		z=selectedElement.getZ();
		rotate=selectedElement.getRotate();
		scale=selectedElement.getScale();
		return "";
	}

	public String deleteSlide() throws Exception{
		System.out.println("selectSlide="+getSelectedId());
		System.out.println("size="+impress.children().size());
		//Util.removeElement(impress, selectedElement.getElement());
		selectedElement.getElement().remove();
		System.out.println("new size="+impress.children().size());
		saveFile();
		editor="";
		getValues();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", "Deleted"));  
		return "";
	}
	
	public String saveFile() throws Exception{
		
		File file = new File(getFile());
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		fileOutputStream.write(impress.ownerDocument().html().getBytes());
		System.out.println("size="+impress.children().size());
		
		fileOutputStream.close();
		
		return "";
	}
	public String save() throws Exception{
		selectedElement.getElement().empty();
		selectedElement.getElement().append(editor);
		
		selectedElement.getElement().attr("data-x", x);
		selectedElement.getElement().attr("data-y", y);
		selectedElement.getElement().attr("data-z", z);
		if(scale=="")
			scale="1";
		selectedElement.getElement().attr("data-scale", scale);
		selectedElement.getElement().attr("data-rotate", rotate);
		
		
		saveFile();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", "Saved"));  
		return "";
	}
	public String resetFile() throws Exception{
		Util.resetFile(getOrijinalFile(),getFile() );
		getValues();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", "Reset"));  
		return "";
	}
	
	public String parsePPT() throws Exception{
		XMLSlideShow ppt = new XMLSlideShow(new FileInputStream("D:/calismalar/htmlslide/PowerPointParser/test.pptx"));
		ppt = new XMLSlideShow(new FileInputStream("D:/ramazan/sunum/test1.pptx"));
		XSLFSlide[] slides  =    ppt.getSlides();
		
		File directory = FileUtil.createEmptyDirectory(FacesContext.getCurrentInstance(), "deneme");
		
		getValues();
		int defaultElementSize = getElementList().size();
		
		for (int i = 0; i < slides.length; i++) {
	    	XSLFSlide slide = slides[i];
	    	CustomElement customElement;
	    	
	    	if(i<defaultElementSize){
	    		customElement = getElementList().get(i);
	    		customElement.getElement().empty();
			}else{	
	    		customElement = createCustomElement();  
	    		customElement.getElement().attr("data-x", String.valueOf(1600*i));
	    	}
	    	
	    	XSLFCommonSlideData slideData =  slide.getCommonSlideData();
	    	Iterator<DrawingParagraph> texts= slideData.getText().iterator();
	    	while (texts.hasNext()) {
				DrawingParagraph drawingParagraph = (DrawingParagraph) texts.next();
				String temp = drawingParagraph.getText().toString();
				String text  = new String(temp.getBytes(), "UTF-8");
				if(slide.getTitle().equals(drawingParagraph.getText().toString())){
					customElement.getElement().append("<p style=\"text-align: left;\"><span><font color=\"#ffff00\">"+temp+"</font></span></p>");
					customElement.getElement().append("<hr></hr>");
				}
				else
					customElement.getElement().append("<p>"+drawingParagraph.getText()+"</p>");
			}
		
	    	Iterator<POIXMLDocumentPart > relations =slide.getRelations().iterator();
	    	while (relations.hasNext()) {
				POIXMLDocumentPart poixmlDocumentPart = (POIXMLDocumentPart) relations.next();
				if(poixmlDocumentPart instanceof XSLFPictureData){
					XSLFPictureData pictureData = (XSLFPictureData)poixmlDocumentPart;
					InputStream in = new ByteArrayInputStream(pictureData.getData());
					BufferedImage bufferedImage = ImageIO.read(in);directory.getPath();
					File imageFile = new File(directory.getAbsolutePath()+"/"+pictureData.getFileName());
			        System.out.println("slide title="+slide.getTitle()+"dosya ismi = "+pictureData.getFileName());
					ImageIO.write(bufferedImage, "jpg", imageFile);
					FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
			        customElement.getElement().append("<p><img WIDTH=1000 HEIGHT=500 src=\""+"../"+directory.getName()+"/"+imageFile.getName()+"\"></p>");
				}
				System.out.println("test1");
				
			}
	    	
	    	if(i>defaultElementSize)
	    	 getElementList().add(customElement);
	    	
		}
		
		saveFile();
		getValues();
		return "";
	}
	
	public Element createElement(){
		Element newElement = impress.appendElement("div");
		newElement.attr("id", "slide"+String.valueOf(impress.children().size()-1));
		newElement.attr("class", "step");
		newElement.attr("data-x", "0");
		newElement.attr("data-y", "0");
		newElement.attr("data-z", "0");
		newElement.attr("data-scale", "1");
		//newElement.attr("data-rotate", "");		
		return newElement;
	}
	
	public CustomElement createCustomElement(){
		CustomElement customElement = new CustomElement();
		customElement.setElement(createElement());
		return customElement;
	}
	
	public String newSlide(){
		
		Element newElement = impress.appendElement("div");
		newElement.attr("id", "slide"+String.valueOf(impress.children().size()-1));
		newElement.attr("class", "step");
		
		
		CustomElement customElement = new CustomElement();
		customElement.setElement(newElement);
		getElementList().add(customElement);
		selectedElement = customElement;
		editor = "";
		return "";
	}
	
	public String getFile(){
		 String test1 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Demo/impress.html");
		  //test1="C:/Users/002834/Desktop/OTV_JSF_Spring_Hibernate/OTV_JSF_Spring_Hibernate/src/main/webapp/Demo/impress.html";
		 /*		
	        String test2 = request.getSession().getServletContext().getRealPath("/");
	        String test3 = request.getRealPath("");
	        String test4 = request.getRealPath("/");
	        String test5 = request.getSession().getServletContext().getRealPath(request.getServletPath());
	        
	        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	        HttpServletResponse res = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
*/
		 return test1;
	}
	public String getOrijinalFile(){
		//return "C:/Users/002834/Desktop/OTV_JSF_Spring_Hibernate/OTV_JSF_Spring_Hibernate/src/main/webapp/Demo/impressOrijinal.html";
		 return  FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Demo/impressOrijinal.html");
	}
	
	/**
	 * Add User
	 * 
	 * @return String - Response Message
	 */
	public String addUser() {
		try {
			User user = new User();
			user.setId(getId());
			user.setName(getName());
			user.setSurname(getSurname());
			getUserService().addUser(user);
			return SUCCESS;
		} catch (DataAccessException e) {
			e.printStackTrace();
		} 	
		
		return ERROR;
	}
	
	/**
	 * Reset Fields
	 * 
	 */
	public void reset() {
		this.setId(0);
		this.setName("");
		this.setSurname("");
	}
	
	/**
	 * Get User List
	 * 
	 * @return List - User List
	 */
	public List<User> getUserList() {
		userList = new ArrayList<User>();
		userList.addAll(getUserService().getUsers());
		return userList;
	}
	
	/**
	 * Get User Service
	 * 
	 * @return IUserService - User Service
	 */
	public IUserService getUserService() {
		return userService;
	}

	/**
	 * Set User Service
	 * 
	 * @param IUserService - User Service
	 */
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	/**
	 * Set User List
	 * 
	 * @param List - User List
	 */
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
	/**
	 * Get User Id
	 * 
	 * @return int - User Id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set User Id
	 * 
	 * @param int - User Id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get User Name
	 * 
	 * @return String - User Name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set User Name
	 * 
	 * @param String - User Name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get User Surname
	 * 
	 * @return String - User Surname
	 */
	public String getSurname() {
		return surname;
	}
	
	/**
	 * Set User Surname
	 * 
	 * @param String - User Surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<SelectItem> getProjectNames() {
		projectNames = FileUtil.getProjectNames(WORKING_DIRECTORY);
		return projectNames;
	}

	public void setProjectNames(List<SelectItem> projectNames) {
		this.projectNames = projectNames;
	}

	public String getSelectedProject() {
		return selectedProject;
	}

	public void setSelectedProject(String selectedProject) {
		this.selectedProject = selectedProject;
	}
	
}