package com.kc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class FileUtils {
	
	public static String copyFile(File source , String destination, String newFileName)
	{
		InputStream inStream = null;
		OutputStream outStream = null;
	 
	    	try{
	    	    File dest =new File(destination);
	    	    
	    	    inStream = new FileInputStream(source);
	    	    outStream = new FileOutputStream(dest + "\\" + newFileName);
	    	    byte[] buffer = new byte[1024];
	    	    int length; 
	    	    while ((length = inStream.read(buffer)) > 0)
	    	    {
	    	    	outStream.write(buffer, 0, length);
	    	    }
	 
	    	    inStream.close();
	    	    outStream.close();
		 
	    	}catch(IOException e){
	    		e.printStackTrace();
	    	}
	    	return destination + "\\" + newFileName;
	}   
	public static void deleteFile(File fileToDelete)
	{
		try
		{
			fileToDelete.delete();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String createPDF(File sourceFile, String destFolder) {
        
		String newFile ="";
		try {
        	
			newFile =  destFolder + "\\" + sourceFile.getName().replace(getExtension(sourceFile), "PDF");
            long start = System.currentTimeMillis();
 
            // 1) Load DOCX into XWPFDocument
            InputStream is = new FileInputStream(sourceFile);
            XWPFDocument document = new XWPFDocument(is);
 
            // 2) Prepare Pdf options
            PdfOptions options = PdfOptions.create();
 
            // 3) Convert XWPFDocument to Pdf
            OutputStream out = new FileOutputStream(new File(newFile));
            PdfConverter.getInstance().convert(document, out, options);
             
            System.err.println("Generate pdf/HelloWorld.pdf with "
                    + (System.currentTimeMillis() - start) + "ms");
             
        } catch (Throwable e) {
            e.printStackTrace();
        }
		return newFile;
    }
	
	public static String getExtension(File sourceFile)
	{
		String extension = "";

    	int i = sourceFile.getName().lastIndexOf('.');
    	if (i > 0) {
    	    extension = sourceFile.getName().substring(i+1);
    	}
    	return extension;
	}

}
