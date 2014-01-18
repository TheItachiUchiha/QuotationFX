package com.kc.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javafx.concurrent.Task;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.kc.constant.CommonConstants;
import com.kc.dao.EnquiryDAO;

public class Email extends Task
{
	EnquiryDAO enquiryDAO = new EnquiryDAO();
	Encryption encryption = new Encryption("");
	static Map<String, String> data;
	
	public Email()
	{}
	public Email(Map<String, String> data)
	{
		this.data = new HashMap<String, String>();
		this.data = data;
	}
	public void sendEmailFromGmail(Map<String, String> data)
	{
		if(null==this.data)
		{
			Email.data = new HashMap<String, String>();
			Email.data = data;
		}
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		try {
 
			
			Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(Email.data.get(CommonConstants.EMAIL_USERNAME), encryption.decrypt(Email.data.get(CommonConstants.EMAIL_PASSWORD)));
						}
					});
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("support@kryptcode.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(data.get(CommonConstants.EMAIL_TO)));
			if(!data.keySet().contains(CommonConstants.EMAIL_SUBJECT))
			{
				message.setSubject("Testing Subject");
			}
			else
			{
				message.setSubject(data.get(CommonConstants.EMAIL_SUBJECT));
			}
			
             
            if(data.keySet().contains(CommonConstants.EMAIL_ATTACHMENT))
            {
            	if( !data.get(CommonConstants.EMAIL_ATTACHMENT).equals(""))
            	{
            		// create the message part   
                    MimeBodyPart messageBodyPart =   
                      new MimeBodyPart();  
                    //fill message  
                    messageBodyPart.setText(data.get(CommonConstants.EMAIL_BODY));  
                    Multipart multipart = new MimeMultipart();  
                    multipart.addBodyPart(messageBodyPart);  
                    // Part two is attachment  
                    messageBodyPart = new MimeBodyPart(); 
	            	DataSource source =   
	            			new FileDataSource(data.get(CommonConstants.EMAIL_ATTACHMENT));
	            	messageBodyPart.setDataHandler(  
	                        new DataHandler(source)); 
	            	 messageBodyPart.setFileName(data.get(CommonConstants.EMAIL_ATTACHMENT));  
	            	 multipart.addBodyPart(messageBodyPart);  
	                 // Put parts in message  
	                 message.setContent(multipart);
            	}
            	 else
                 {
                 	message.setText(data.get(CommonConstants.EMAIL_BODY));
                 }
            }
            else
            {
            	message.setText(data.get(CommonConstants.EMAIL_BODY));
            }
            
			Transport.send(message);
 
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	protected Object call() throws Exception {
		// TODO Auto-generated method stub
		sendEmailFromGmail(this.data);
		return null;
	}
}
