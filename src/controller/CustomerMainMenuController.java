package controller;

import driver.StopNShop;
import model.User;
import view.ConsumerProductsView;
import view.CustomerMainMenu;
import view.Login;
import view.SellerPortal;
import view.Settings;

public class CustomerMainMenuController {
	private CustomerMainMenu view;
	private Settings settingsview;
	private SellerPortal sellerPortal;
	private ConsumerProductsView products;
	private StopNShop program;
	private User account;
	
	public CustomerMainMenuController(CustomerMainMenu view, StopNShop program, User account) {
		this.view = view;
		settingsview = null;
		sellerPortal = null;
		products = null;
		this.program = program;
		this.account = account;
		view.addController(this);
	}
	
	public void toggleProducts()
	{
		if(products == null)
		{
			products = new ConsumerProductsView();
			new ConsumerProductsController(products, program, account, this);
		}
		else
		{
			products.toFront();
			products.revalidate();
			products.repaint();
		}
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
			sellerPortal.toFront();
			sellerPortal.revalidate();
			sellerPortal.repaint();
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
		
		if(sellerPortal != null)
			sellerPortal.dispose();
		
		if(products != null)
			products.dispose();
		
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
			settingsview.revalidate();
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

	public ConsumerProductsView getProducts() {
		return products;
	}

	public void setProducts(ConsumerProductsView products) {
		this.products = products;
	}
}
