package controller;

import driver.StopNShop;
import model.User;
import view.CartView;
import view.ConsumerProductsView;
import view.CustomerMainMenu;
import view.Login;
import view.SellerPortal;
import view.Settings;

public class CustomerMainMenuController {
	private CustomerMainMenu view;
	private Settings settingsview;
	private SellerPortal sellerPortal;
	private CartView cart;
	private ConsumerProductsView products;
	private StopNShop program;
	private User account;
	
	public CustomerMainMenuController(CustomerMainMenu view, StopNShop program, User account) {
		this.view = view;
		settingsview = null;
		sellerPortal = null;
		products = null;
		cart = null;
		this.program = program;
		this.account = account;
		view.addController(this);
	}
	
	public void toggleCart()
	{
		if(cart == null)
		{
			cart = new CartView();
			new CartController(cart, account, this);
		}
		else
		{
			cart.toFront();
			cart.revalidate();
			cart.repaint();
		}
	}
	
	public void toggleProducts()
	{
		if(products == null)
		{
			products = new ConsumerProductsView();
			new ConsumerProductsController(products, account, this);
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
			new SellerPortalController(sellerPortal, account, this);
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
		
		if(cart != null)
			cart.dispose();
		
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

	public CartView getCart() {
		return cart;
	}

	public void setCart(CartView cart) {
		this.cart = cart;
	}
	
	
}
