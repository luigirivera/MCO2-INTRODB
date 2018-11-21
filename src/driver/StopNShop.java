package driver;

import controller.CorporateMainMenuController;
import controller.CustomerMainMenuController;
import controller.LoginController;
import model.User;
import view.CorporateMainMenu;
import view.CustomerMainMenu;
import view.Login;

public class StopNShop {
	private User account;
	
	public StopNShop() {
		new LoginController(new Login(), this);
	}
	
	public void login()
	{
		if(!account.isCorporate()) new CustomerMainMenuController(new CustomerMainMenu(), this, account);
		else new CorporateMainMenuController(new CorporateMainMenu(), this, account);
	}
	
	public User getAccount() {
		return account;
	}
	public void setAccount(User account) {
		this.account = account;
	}
}
