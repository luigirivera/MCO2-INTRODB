package controller;

import model.User;
import view.SalesView;
import view.SellerPortal;
import view.SellerProducts;

public class SellerPortalController {
	private SellerPortal view;
	private SalesView sales;
	private SellerProducts products;
	private User account;
	private CustomerMainMenuController cmmc;
	
	public SellerPortalController(SellerPortal view, User account, CustomerMainMenuController cmmc) {
		this.view = view;
		this.account = account;
		this.cmmc = cmmc;
		products = null;
		sales = null;
		view.addController(this);
		
		update();
	}
	
	private void update()
	{
		view.getFollowers().setText("Followers: " + account.getFollowers());
		view.getFollowing().setText("Following: " + account.getFollowing());
	}
	
	public void close()
	{
		view.dispose();
		if(products != null)
			products.dispose();
		if(sales != null)
			sales.dispose();
		cmmc.setSellerPortal(null);
	}
	
	public void toggleSales()
	{
		if(sales == null)
		{
			sales = new SalesView();
			new SalesController(sales, account, this);
		}
		else
		{
			sales.toFront();
			sales.revalidate();
			sales.repaint();
		}
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

	public SalesView getSales() {
		return sales;
	}

	public void setSales(SalesView sales) {
		this.sales = sales;
	}
	
	
}
