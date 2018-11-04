package controller;

import driver.StopNShop;
import model.User;
import view.CustomerMainMenu;
import view.Login;
import view.Settings;

public class CustomerMainMenuController {
	private CustomerMainMenu view;
	private Settings settingsview;
	private StopNShop program;
	private User account;
	
	public CustomerMainMenuController(CustomerMainMenu view, StopNShop program, User account) {
		this.view = view;
		settingsview = null;
		this.program = program;
		this.account = account;
		view.addController(this);
	}
	
	public void logout()
	{
		new LoginController(new Login(), program);
		view.dispose();
		settingsview.clear();
		settingsview.dispose();
	}
	
	public void toggleSettings()
	{
		if(settingsview == null)
		{
			settingsview = new Settings();
			new SettingsController(settingsview, program, account);
		}
		else
		{
			settingsview.toFront();
			settingsview.repaint();
		}
	}
}
