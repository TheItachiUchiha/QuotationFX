package com.kc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javafx.scene.control.Dialogs;

import com.kc.constant.CommonConstants;
import com.kc.controller.LoginController;

public class FileUtils {
	
	public static void copyFile(String source , String destination)
	{
		InputStream inStream = null;
		OutputStream outStream = null;
	 
	    	try{
	 
	    	    File src =new File(source);
	    	    File dest =new File(destination);
	    	    
	    	    if(src.exists())
	    	    {
		    	    inStream = new FileInputStream(src);
		    	    outStream = new FileOutputStream(dest);
		    	    byte[] buffer = new byte[1024];
		    	    int length; 
		    	    while ((length = inStream.read(buffer)) > 0)
		    	    {
		    	    	outStream.write(buffer, 0, length);
		    	    }
		 
		    	    inStream.close();
		    	    outStream.close();
	    	    }
	    	    else
	    	    {
	    	    	Dialogs.showErrorDialog(LoginController.primaryStage, CommonConstants.FILE_ACCESS_FAILED_MSG, CommonConstants.FILE_ACCESS_FAILED);
	    	    }
		 
	    	}catch(IOException e){
	    		e.printStackTrace();
	    	}
	}     

}
