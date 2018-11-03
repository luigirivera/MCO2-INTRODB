package controller;

import java.awt.Color;
import java.util.Calendar;

import driver.StopNShop;
import model.GENDER;
import model.User;
import view.Settings;

public class SettingsController {
	private Settings view;
	private StopNShop program;
	private User account;
	
	public SettingsController(Settings view, StopNShop program, User account) {
		this.view = view;
		this.program = program;
		this.account = account;
		view.addController(this);
		configureFields();
	}
	
	public void configureFields()
	{
		Color fg = Color.BLACK;
		view.getUsername().setText(account.getUsername());
		view.getUsername().setForeground(fg);
		if(account.getEmail() != null)
		{
			view.getEmail().setText(account.getEmail());
			view.getEmail().setForeground(fg);
		}
		if(account.getNumber() != 0)
		{
			view.getNumber().setText(String.valueOf(account.getNumber()));
			view.getNumber().setForeground(fg);
		}
		
		if(account.getGender() != null)
			if(account.getGender().equals(GENDER.M.toString()))
				view.getMale().setSelected(true);
			else
				view.getFemale().setSelected(true);
		
		
		
		if(account.getBirthday() != null)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(account.getBirthday());
			view.getYear().setSelectedItem(cal.get(Calendar.YEAR));
			view.getMonth().setSelectedIndex(cal.get(Calendar.MONTH + 1));
			view.getDate().setSelectedItem(cal.get(Calendar.DATE));
		}
		
	}
}
