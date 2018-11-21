package controller;

import driver.StopNShop;
import model.User;
import view.CorporateMainMenu;
import view.CorporateProductsView;
import view.Login;

public class CorporateMainMenuController {
	private CorporateMainMenu view;
	private StopNShop program;
	private CorporateProductsView products;
	private User account;
	
	public CorporateMainMenuController(CorporateMainMenu view, StopNShop program, User account) {
		this.view = view;
		this.program = program;
		this.account = account;
		view.addController(this);
	}
	
	public void logout()
	{
		new LoginController(new Login(), program);
		view.dispose();	
		
		if(products != null) products.dispose();
	}

	public void toggleProducts() {
		if(products == null)
		{
			products = new CorporateProductsView();
			new CorporateProductsController(products, account, program, this);
		}
		else
		{
			products.revalidate();
			products.repaint();
		}
		
	}

}
