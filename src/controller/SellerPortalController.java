package controller;

import model.User;
import view.SellerPortal;
import view.SellerProducts;

public class SellerPortalController {
	private SellerPortal view;
	private SellerProducts products;
	private User account;
	private CustomerMainMenuController cmmc;
	
	public SellerPortalController(SellerPortal view, User account, CustomerMainMenuController cmmc) {
		this.view = view;
		this.account = account;
		this.cmmc = cmmc;
		products = null;
		view.addController(this);
	}
	
	public void close()
	{
		view.dispose();
		cmmc.setSellerPortal(null);
	}
	
	public void toggleProducts()
	{
		if(products == null)
		{
			products = new SellerProducts();
			new SellerProductsController(products, account, this);
		}
		else
		{
			products.toFront();
			products.revalidate();
			products.repaint();
		}
	}

	public SellerProducts getProducts() {
		return products;
	}

	public void setProducts(SellerProducts products) {
		this.products = products;
	}
	
}
