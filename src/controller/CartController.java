package controller;

import java.util.ArrayList;

import driver.StopNShop;
import model.Cart;
import model.CartTableModel;
import model.Product;
import model.SellerProductsTableModel;
import model.User;
import view.CartView;

public class CartController {
	private CartView view;
	private StopNShop program;
	private User account;
	private CustomerMainMenuController mainMenu;
	private CartTableModel modelCartTable;
	public CartController(CartView view, StopNShop program, User account,
			CustomerMainMenuController mainMenu) {
		
		this.view = view;
		this.program = program;
		this.account = account;
		this.mainMenu = mainMenu;
		modelCartTable = null;
		view.addController(this);
		
		update();
	}
	
	public void update()
	{
		Cart c = new Cart();
		c.setUserID(account.getId());
		ArrayList<Cart> cart = c.getCartOfUser();
		
		if(modelCartTable == null)
			modelCartTable = new CartTableModel(cart);
		else
			modelCartTable.setCart(cart);
		
		for(int i = view.getModelCartTable().getRowCount() - 1; i >= 0; i--)
			view.getModelCartTable().removeRow(i);
		
		if(modelCartTable.getRowCount() > 0 && modelCartTable.getCartAt(0).getName() != null)
			for(int i = 0; i < modelCartTable.getRowCount(); i++)
			{
				Cart cc = modelCartTable.getCartAt(i);
				Object[] row = new Object[] {cc.getName(), cc.getQuantity(), cc.getDiscount(), cc.getPrice(), cc.getShipping(), cc.getTotal()};
				
				view.getModelCartTable().addRow(row);
			}
	}
	
	public void close()
	{
		view.dispose();
		mainMenu.setCart(null);
	}

}
