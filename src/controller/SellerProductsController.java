package controller;

import model.SellerProductsTableModel;
import model.User;
import view.SellerProducts;

public class SellerProductsController {
	private SellerProducts view;
	private User account;
	private SellerPortalController portal;
	private SellerProductsTableModel productsTableModel;
	
	public SellerProductsController(SellerProducts view, User account, SellerPortalController portal)
	{
		this.view = view;
		this.account = account;
		this.portal = portal;
		productsTableModel = null;
		view.addController(this);
		update();
	}
	
	public void delete()
	{
		
	}
	
	public void close()
	{
		view.dispose();
		portal.setProducts(null);
	}
	
	public void update()
	{
		
	}
}
