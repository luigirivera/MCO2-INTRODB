package controller;

import java.util.ArrayList;

import driver.StopNShop;
import model.Address;
import model.BankAccount;
import model.Card;
import model.Cart;
import model.CartTableModel;
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
	
	public void checkout(int yes, int no)
	{
		Order order = new Order();
		
		Address a = new Address();
		a.setUserID(account.getId());
		order.setAddressID(view.selectAddress(a.getAddressesOfUser()));
		
		Card card = new Card();
		card.setUserID(account.getId());
		BankAccount bA = new BankAccount();
		bA.setUserID(account.getId());
		view.selectPayment(card.getCardsOfUser(), bA.getAccountsOfUser());
		if(ba selected)
			order.setBankAccountID();
		else
			order.setCardID();
		
		
		Cart c = new Cart();
		c.setUserID(account.getId());
		ArrayList<Cart> cart = c.getCartOfUser();

		for(Cart cc : cart)
			if(cc.getQuantity() > cc.getProductStock(cc.getProductID()))
			{
				int result = view.toggleStockError(cc.getQuantity(), cc.getProductStock(cc.getProductID()), cc.getName());
				if(result == yes)
				{
					cc.updateQuantity(cc.getProductStock(cc.getProductID()));
					update();
					addtoorder;
					deletefromcart;
					update();
				}
					
			}
			else
			{
				update();
				addtoorder;
				deletefromcart;
				update();
			}
	}
	
	public void delete()
	{
		Cart c = modelCartTable.getCartAt(view.getCartTable().getSelectedRow());
		
		c.delete();
		
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
