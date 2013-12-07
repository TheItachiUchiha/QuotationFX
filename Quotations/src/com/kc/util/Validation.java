package com.kc.util;

/**
 * 
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import com.mytdev.javafx.scene.control.AutoCompleteTextField;

/**
 * @author Pragati
 *
 */
public class Validation 
{
	
	static int i=0;
	public boolean isEmpty(String temp){
	Pattern p =Pattern.compile(" ");
	 Matcher m = p.matcher(temp);
	temp=m.replaceAll("");
	
		if ((temp==null)||("".equals(temp))){
			return true;
		}
		
		else{
			
		
		return false;
		}
	}
	
	public boolean isEmpty(TextField textField){
		Pattern p =Pattern.compile(" ");
		 Matcher m = p.matcher(textField.getText());
		 String temp=textField.getText();
		 temp=m.replaceAll("");
		
			if ((temp==null)||("".equals(temp))){
				textField.requestFocus();
				return true;
			}
			
			else{
				
			
			return false;
			}
		}
	
	public boolean isEmpty(Object...args){
		for(int i=0;i<args.length;i++)
		{
			TextField textField = (TextField) args[i];
			Pattern p =Pattern.compile(" ");
			Matcher m = p.matcher(textField.getText());
			String temp=textField.getText();
			temp=m.replaceAll("");
			if ((temp==null)||("".equals(temp))){
				textField.requestFocus();
				return true;
			}	
		}
		return false;
		}
	public boolean compareDates(String str1, String str2) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	Date date1 = sdf.parse(str1);
    	Date date2 = sdf.parse(str2);
    	

    	if(date1.compareTo(date2)>0){
    		return false;
    	
    	}else{
    		return true;
    	}

		
	
		}
	
	public boolean isEmpty(AutoCompleteTextField textField){
		Pattern p =Pattern.compile(" ");
		 Matcher m = p.matcher(textField.getText());
		 String temp=textField.getText();
		 temp=m.replaceAll("");
		
			if ((temp==null)||("".equals(temp))){
				textField.requestFocus();
				return true;
			}
			
			else{
				
			
			return false;
			}
		}
	public boolean isInvalidDate(TextField textField) {
		String strdate= new String();
		String text=textField.getText();
		int tempdate;
		if (text == null || !text.matches("^[0-3][0-9]/[0-1]?[0-9]/[0-9]{4}$")){
		
			textField.requestFocus();
			return true;
			
		}
		else{
			strdate=text.substring(0,2);
			tempdate = Integer.parseInt(strdate);
			if (tempdate>31)
					return true;
					
		}
	       
	    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	    df.setLenient(false);
	    try {
	        df.parse(text);
	        return false;
	    } catch (Exception ex) {
	        return true;
	    }
	}
	public boolean isInvalidDate(String text) {
		String strdate= new String();
		int tempdate;
		if (text == null || !text.matches("^[0-3][0-9]/[0-1]?[0-9]/[0-9]{4}$")){
		
		
			return true;
			
		}
		else{
			strdate=text.substring(0,2);
			tempdate = Integer.parseInt(strdate);
			if (tempdate>31)
					return true;
					
		}
	       
	    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	    df.setLenient(false);
	    try {
	        df.parse(text);
	        return false;
	    } catch (Exception ex) {
	        return true;
	    }
	}
	
public boolean isWord(String temp){
	  Pattern p = Pattern.compile("[a-zA-Z ]+");
	  Matcher m = p.matcher(temp);
	  return m.matches();
	}
public boolean isEmail(String temp){
	  Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	  Matcher m = p.matcher(temp);
	  return m.matches();
	}

public void allowDigit(final TextField fieldName){
	
	fieldName.lengthProperty().addListener(new ChangeListener<Number>(){
		 
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {              
			 
			 if(newValue.intValue() > oldValue.intValue()){
				char ch = fieldName.getText().charAt(oldValue.intValue());                   
	 
				//Check if the new character is the number or other's
				if(!(ch >= '0' && ch <= '9' )){       
	                 
					//if it's not number then just setText to previous one
					fieldName.setText(fieldName.getText().substring(0,fieldName.getText().length()-1)); 
				}
			}
		}
		
	});
}


public void allowAsEmail(final TextField fieldName){
	
	fieldName.lengthProperty().addListener(new ChangeListener<Number>(){
		 
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {              
			 
			 if(newValue.intValue() > oldValue.intValue()){
				char ch = fieldName.getText().charAt(oldValue.intValue());
				//System.out.println("Length:"+ oldValue+"  "+ newValue +" "+ch);                   
	 
				//Check if the new character is the number or other's
				if((isEmail(fieldName.getText()))){       
	                 
					//if it's not number then just setText to previous one
					fieldName.setText(fieldName.getText().substring(0,fieldName.getText().length()-1)); 
				}
			}
		}
		
	});
}
public void allowCharacters(final TextField fieldName){
	
	fieldName.lengthProperty().addListener(new ChangeListener<Number>(){
		 
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {              
			 
			 if(newValue.intValue() > oldValue.intValue()){
				char ch = fieldName.getText().charAt(oldValue.intValue());
				//System.out.println("Length:"+ oldValue+"  "+ newValue +" "+ch);                   
	 
				//Check if the new character is the number or other's
				if(!((ch >= 'a' && ch <= 'z' )||(ch >= 'A' && ch <= 'Z' )||(ch == ' '))){       
	                 
					//if it's not number then just setText to previous one
					fieldName.setText(fieldName.getText().substring(0,fieldName.getText().length()-1)); 
				}
			}
		}
		
	});
}


public void allowAsPhoneNumber(final TextField fieldName){
	
	fieldName.lengthProperty().addListener(new ChangeListener<Number>(){
		 
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {              
			 
			 if(newValue.intValue() > oldValue.intValue()){
				char ch = fieldName.getText().charAt(oldValue.intValue());
				//System.out.println("Length:"+ oldValue+"  "+ newValue +" "+ch);                   
	 
				//Check if the new character is the number or other's
				if(!((ch >= '0' && ch <= '9' )&&(fieldName.getText().length()<=10))){       
	                 
					//if it's not number then just setText to previous one
					fieldName.setText(fieldName.getText().substring(0,fieldName.getText().length()-1)); 
				}
			}
		}
		
	});
}

public void allowAsPercentage(final TextField fieldName){
	
	fieldName.lengthProperty().addListener(new ChangeListener<Number>(){
		 
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {              
			 
			 if(newValue.intValue() > oldValue.intValue()){
				char ch = fieldName.getText().charAt(oldValue.intValue());
				//System.out.println("Length:"+ oldValue+"  "+ newValue +" "+ch);   
				if(fieldName.getText().length()>2)
				{
					if(!fieldName.getText().contains("."))
					{
	                	 fieldName.setText(fieldName.getText().substring(0,fieldName.getText().length()-1));
					}
				}
				if(fieldName.getText().indexOf('.')!=fieldName.getText().lastIndexOf('.'))
                {
					fieldName.setText(fieldName.getText().substring(0,fieldName.getText().length()-1)); 
                }
				else if(fieldName.getText().contains("."))
				{
					if(fieldName.getText().length()-fieldName.getText().lastIndexOf('.')>3)
	                {
						fieldName.setText(fieldName.getText().substring(0,fieldName.getText().length()-1)); 
	                }
				}
						
				else if(!((ch >= '0' && ch <= '9' )||(ch=='.')&&(fieldName.getText().length()<=4)))
				{       
                	 fieldName.setText(fieldName.getText().substring(0,fieldName.getText().length()-1)); 
				}
			}
		}
		
	});
}

public void allowAsDate(final TextField fieldName){
	
	fieldName.lengthProperty().addListener(new ChangeListener<Number>(){
		 
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {              
			 
			 if(newValue.intValue() > oldValue.intValue()){
				char ch = fieldName.getText().charAt(oldValue.intValue());
				//System.out.println("Length:"+ oldValue+"  "+ newValue +" "+ch);                   
	 
				//Check if the new character is the number or other's
				if(!((ch >= '0' && ch <= '9' || ch =='-'))){       
	                 
					//if it's not number then just setText to previous one
					fieldName.setText(fieldName.getText().substring(0,fieldName.getText().length()-1)); 
				}
			}
		}
		
	});
}
public void allowAsAmount(final TextField fieldName){
	
	fieldName.lengthProperty().addListener(new ChangeListener<Number>(){
		 
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {              
			 
			 if(newValue.intValue() > oldValue.intValue()){
				char ch = fieldName.getText().charAt(oldValue.intValue());
				//System.out.println("Length:"+ oldValue+"  "+ newValue +" "+ch);                   
	 
				//Check if the new character is the number or other's
				if(fieldName.getText().indexOf('.')!=fieldName.getText().lastIndexOf('.'))
                {
					fieldName.setText(fieldName.getText().substring(0,fieldName.getText().length()-1)); 
                }
				else if(fieldName.getText().contains("."))
				{
					if(fieldName.getText().length()-fieldName.getText().lastIndexOf('.')>3)
	                {
						fieldName.setText(fieldName.getText().substring(0,fieldName.getText().length()-1)); 
	                }
				}
						
				
				else if(!((ch >= '0' && ch <= '9' )||(ch=='.')))
				{       
					
	                	
	                
	                	 fieldName.setText(fieldName.getText().substring(0,fieldName.getText().length()-1)); 
	                 
					//if it's not number then just setText to previous one
	                
				}
                
				
			}
		}
		
	});
}

public void removeMessageOnTextFieldClick(TextField textField, final Label msg)
{
	textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
	    @Override
	    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	        if(!oldValue) {
	            msg.setText("");
	        }
	    }
	});
}

public void removeMessageOnComboBoxClick(ComboBox comboBox, final Label msg)
{
	comboBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
	    @Override
	    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	        if(!oldValue) {
	            msg.setText("");
	        }
	    }
	});
}



}
