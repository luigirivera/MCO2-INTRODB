package controller;

import view.Login;

public class LoginController {
	private Login view;
	
	public LoginController(Login view)
	{
		this.view = view;
		view.addController(this);
	}
}
