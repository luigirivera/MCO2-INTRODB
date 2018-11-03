package driver;

import controller.LoginController;
import model.User;
import view.Login;

public class StopNShop {
	private User account;
	public StopNShop() {
		new LoginController(new Login(), this);
	}
	
	public void login()
	{
		
	}
	
	public User getAccount() {
		return account;
	}
	public void setAccount(User account) {
		this.account = account;
	}
}
