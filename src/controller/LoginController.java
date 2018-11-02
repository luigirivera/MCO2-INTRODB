package controller;

import model.User;
import view.Login;

public class LoginController {
	private Login view;
	
	public LoginController(Login view)
	{
		this.view = view;
		view.addController(this);
	}
	
	public boolean doesExist() {
		User user = new User();
		
		return user.doesExist();
	}
}
