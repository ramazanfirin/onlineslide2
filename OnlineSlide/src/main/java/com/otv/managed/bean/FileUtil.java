package com.otv.managed.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

public class FileUtil {

	public static File createEmptyDirectory(FacesContext facesContext,String name){
		File directory = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("")+"/"+name);
		if (!directory.exists()) {
			if (directory.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
				
			}
		}else{
			String[] myFiles; 
			myFiles = directory.list();  
            for (int i=0; i<myFiles.length; i++) {  
                File myFile = new File(directory, myFiles[i]);   
                myFile.delete();  
            }  
		}
		return directory;
	}
	
	public static void exportToZip(String name){
		try {
			File outputFile =new File("c:/OnlineSlide/deneme/"+name+".zip"); 
			FileOutputStream fos = new FileOutputStream(outputFile);
			ZipOutputStream zos = new ZipOutputStream(fos);

			//String file1Name = "file1.txt";
			//String file2Name = "file2.txt";
			//String file3Name = "folder/file3.txt";
			//String file4Name = "folder/file4.txt";
			//String file5Name = "f1/f2/f3/file5.txt";
			
			String directory = "D:/calismalar/htmlslide/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/OnlineSlide/deneme";
			String directory2 = "D:/calismalar/htmlslide/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/OnlineSlide/Demo";

			//addToZipFile(file1Name, zos);
			//addToZipFile(file2Name, zos);
			//addToZipFile(file3Name, zos);
			//addToZipFile(file4Name, zos);
			//addToZipFile(file5Name, zos);
			File file = new File(directory);
			if(file.isFile())
				addToZipFile(file, zos);
			if(file.isDirectory()){
				File[] fileList = file.listFiles();
				for (int i = 0; i < fileList.length; i++) {
					File temp = fileList[i];
					addToZipFile(temp, zos);
				}
			}
			
			
			zos.close();
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addToZipFile(File  file, ZipOutputStream zos) throws FileNotFoundException, IOException {

		System.out.println("Writing '" + file.getName() + "' to zip file");

		//File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);
		ZipEntry zipEntry = new ZipEntry(file.getName());
		zos.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}

		zos.closeEntry();
		fis.close();
	}

	public static void main(String[] args){
		
		exportToZip("test");
		String directory = "D:/calismalar/htmlslide/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/OnlineSlide/deneme";
		System.out.println("bitti");
	}
	
	public static List<SelectItem> getProjectNames(String directory){
		List<SelectItem> projectNames = new ArrayList<SelectItem>();
		
		File file = new File(directory);
		File[] fileList = file.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			File temp = fileList[i];
			if(temp.isDirectory())
				projectNames.add(new SelectItem(temp, temp.getName()));
				
		}
		return projectNames;
	}
}
