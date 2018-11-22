package controller;

import driver.StopNShop;
import model.User;
import view.Accounts;
import view.CorporateMainMenu;
import view.CorporateProductsView;
import view.Login;

public class CorporateMainMenuController {
	private CorporateMainMenu view;
	private StopNShop program;
	private CorporateProductsView products;
	private Accounts accounts;
	private User account;
	
	public CorporateMainMenuController(CorporateMainMenu view, StopNShop program, User account) {
		this.view = view;
		this.program = program;
		this.account = account;
		products = null;
		accounts = null;
		
		view.addController(this);
	}
	
	public void logout()
	{
		new LoginController(new Login(), program);
		view.dispose();	
		
		if(products != null) products.dispose();
		if(accounts != null) accounts.dispose();
	}
	
	public void toggleAccounts() {
		if(accounts == null)
		{
			accounts = new Accounts();
			new AccountsController(accounts, account, this);
		}
		else
		{
			accounts.toFront();
			accounts.revalidate();
			accounts.repaint();
		}
		
	}

	public void toggleProducts() {
		if(products == null)
		{
			products = new CorporateProductsView();
			new CorporateProductsController(products, this);
		}
		else
		{
			products.toFront();
			products.revalidate();
			products.repaint();
		}
		
	}

	public CorporateProductsView getProducts() {
		return products;
	}

	public void setProducts(CorporateProductsView products) {
		this.products = products;
	}

	public Accounts getAccounts() {
		return accounts;
	}

	public void setAccounts(Accounts accounts) {
		this.accounts = accounts;
	}
	
	

}
