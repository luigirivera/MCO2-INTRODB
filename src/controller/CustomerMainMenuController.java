package controller;

import driver.StopNShop;
import model.User;
import view.CustomerMainMenu;
import view.Login;
import view.SellerPortal;
import view.Settings;

public class CustomerMainMenuController {
	private CustomerMainMenu view;
	private Settings settingsview;
	private SellerPortal sellerPortal;
	private StopNShop program;
	private User account;
	
	public CustomerMainMenuController(CustomerMainMenu view, StopNShop program, User account) {
		this.view = view;
		settingsview = null;
		sellerPortal = null;
		this.program = program;
		this.account = account;
		view.addController(this);
	}
	
	public void toggleSellerPortal()
	{
		if(sellerPortal == null)
		{
			sellerPortal = new SellerPortal();
			new SellerPortalController(sellerPortal, program, account, this);
		}
		else
		{
			settingsview.toFront();
			settingsview.repaint();
		}
	}
	
	public void logout()
	{
		new LoginController(new Login(), program);
		view.dispose();
		if(settingsview != null)
		{
			settingsview.clear();
			settingsview.dispose();
		}
		
	}
	
	public void toggleSettings()
	{
		if(settingsview == null)
		{
			settingsview = new Settings();
			new SettingsController(settingsview, program, account, this);
		}
		else
		{
			settingsview.toFront();
			settingsview.repaint();
		}
	}

	public Settings getSettingsview() {
		return settingsview;
	}

	public void setSettingsview(Settings settingsview) {
		this.settingsview = settingsview;
	}

	public SellerPortal getSellerPortal() {
		return sellerPortal;
	}

	public void setSellerPortal(SellerPortal sellerPortal) {
		this.sellerPortal = sellerPortal;
	}
	
	
}
