package controller;

import driver.StopNShop;
import model.User;
import view.SellerPortal;

public class SellerPortalController {
	private SellerPortal view;
	private StopNShop program;
	private User account;
	private CustomerMainMenuController cmmc;
	
	public SellerPortalController(SellerPortal view, StopNShop program, User account, CustomerMainMenuController cmmc) {
		this.view = view;
		this.program = program;
		this.account = account;
		this.cmmc = cmmc;
		view.addController(this);
	}
	
	public void close()
	{
		view.dispose();
		cmmc.setSellerPortal(null);
	}
}
