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

import javafx.scene.control.Dialogs;

import com.kc.constant.CommonConstants;
import com.kc.controller.LoginController;

public class FileUtils {
	
	public static void copyFile(File source , String destination)
	{
		InputStream inStream = null;
		OutputStream outStream = null;
	 
	    	try{
	    	    File dest =new File(destination);
	    	    
	    	    inStream = new FileInputStream(source);
	    	    outStream = new FileOutputStream(dest);
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
	}   
	
	public static void createPDF(File sourceFile, String destFolder) {
        try {
            long start = System.currentTimeMillis();
 
            // 1) Load DOCX into XWPFDocument
            InputStream is = new FileInputStream(sourceFile);
            XWPFDocument document = new XWPFDocument(is);
 
            // 2) Prepare Pdf options
            PdfOptions options = PdfOptions.create();
 
            // 3) Convert XWPFDocument to Pdf
            OutputStream out = new FileOutputStream(new File(destFolder + sourceFile.getName()));
            PdfConverter.getInstance().convert(document, out, options);
             
            System.err.println("Generate pdf/HelloWorld.pdf with "
                    + (System.currentTimeMillis() - start) + "ms");
             
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
