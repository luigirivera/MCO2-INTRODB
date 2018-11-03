package driver;

import controller.LoginController;
import view.Login;

public class StopNShop {
	private LoginController loginControl;
	
	public StopNShop() {
		loginControl = new LoginController(new Login(), this);
	}
}
