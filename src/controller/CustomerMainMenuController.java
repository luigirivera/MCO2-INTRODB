package controller;

import driver.StopNShop;
import model.User;
import view.CustomerMainMenu;
import view.Login;

public class CustomerMainMenuController {
	private CustomerMainMenu view;
	private StopNShop program;
	private User account;
	
	public CustomerMainMenuController(CustomerMainMenu view, StopNShop program, User account) {
		this.view = view;
		this.program = program;
		this.account = account;
		view.addController(this);
	}
	
	public void logout()
	{
		new LoginController(new Login(), program);
	}
}
