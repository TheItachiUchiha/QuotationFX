package com.kc.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kc.constant.CommonConstants;
import com.kc.controller.LoginController;
import com.kc.dao.EnquiryDAO;
import com.kc.dao.StatusReminderDAO;
import com.kc.model.ReminderVO;
import com.kc.util.DateUtil;
import com.kc.util.Email;

import javafx.application.Platform;
import javafx.concurrent.Task;

public class ReminderEmailSendService extends Task
{
	private StatusReminderDAO statusReminderDAO;
	private EnquiryDAO enquiryDAO;
	Email email;
	
	public ReminderEmailSendService()
	{
		statusReminderDAO = new StatusReminderDAO();
		enquiryDAO = new EnquiryDAO();
		email = new Email();
	}
	@Override
	protected Object call() throws Exception {
		try
		{
			Date date = new Date();
			String todayDate = DateUtil.formatter.format(date);
			List<ReminderVO> listOReminderVO = statusReminderDAO.getReminderDetailsForSendingEmail(todayDate);
			Map<String, String> emailMap = statusReminderDAO.getReminderMailDetails();
			for(int i=0; i<listOReminderVO.size(); i++)
			{
				final ReminderVO reminderVO = listOReminderVO.get(i);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						LoginController.emailLabel.setText(reminderVO.getReciever());
					}
				});
				
				Map<String, String> map = new HashMap<String, String>();
				map.put(CommonConstants.EMAIL_TO, listOReminderVO.get(i).getReciever());
				map.put(CommonConstants.EMAIL_BODY, listOReminderVO.get(i).getEmailMessage());
				map.put(CommonConstants.EMAIL_SUBJECT, listOReminderVO.get(i).getSubject());
				map.put(CommonConstants.EMAIL_USERNAME, emailMap.get(CommonConstants.KEY_REMINDER_EMAIL));
				map.put(CommonConstants.EMAIL_PASSWORD, emailMap.get(CommonConstants.KEY_REMINDER_PASSWORD));
				email.sendEmailFromGmail(map);
				statusReminderDAO.updateNoOfReminderSent(listOReminderVO.get(i).getReferenceNo());
				Date nextDate = DateUtil.addDays(date, reminderVO.getFrequency());
				reminderVO.setNextSend(DateUtil.formatter.format(nextDate));
				statusReminderDAO.updateReminder(reminderVO, reminderVO.getReferenceNo());
				updateProgress(i+1, listOReminderVO.size());
			}
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					LoginController.emailStage.close();
				}
			});
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
