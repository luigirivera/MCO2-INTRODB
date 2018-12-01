package controller;

import java.util.ArrayList;

import model.Address;
import model.BankAccount;
import model.Card;
import model.Cart;
import model.CartTableModel;
import model.Order;
import model.Product;
import model.User;
import view.CartView;

public class CartController {
	private CartView view;
	private User account;
	private CustomerMainMenuController mainMenu;
	private CartTableModel modelCartTable;
	public CartController(CartView view, User account,
			CustomerMainMenuController mainMenu) {
		
		this.view = view;
		this.account = account;
		this.mainMenu = mainMenu;
		modelCartTable = null;
		view.addController(this);
		
		update();
	}
	
	public void updateCart(int quantity)
	{
		Cart cart = modelCartTable.getCartAt(view.getCartTable().getSelectedRow());
		
		if(quantity == 0)
			cart.delete();
		else
			cart.updateQuantity(quantity);
		
		update();
	}
	
	public String checkQuantityError(int quantity)
	{
		String error = "";
		Cart cart = modelCartTable.getCartAt(view.getCartTable().getSelectedRow());
		Product product = new Product().getProductFromID(cart.getProductID());
		
		if(quantity > product.getStock())
			error += "You have set a number higher than available. Please select a lower number";
		
		return error;
	}
	
	@SuppressWarnings("null")
	public void checkout(int yes, int no)
	{
		Cart cTemp = new Cart();
		cTemp.setUserID(account.getId());
		if(!cTemp.getCartOfUser().isEmpty())
		{
			Order order = new Order();
			
			Address a = new Address();
			a.setUserID(account.getId());
			ArrayList<Address> addresses = a.getAddressesOfUser();
			
			for(Address address : addresses)
				if(address.getLine2() != null)
					view.getAddresses().addItem(address.getLine1() + " " + address.getLine2() + " "  + address.getCity() + " "  + address.getProvince());
				else
					view.getAddresses().addItem(address.getLine1() + " "  + address.getCity() + " "  + address.getProvince());
			
			String ad;
			
			if(!addresses.isEmpty())
			{
				do {
					ad = view.selectAddress();
				}while(ad == null && !ad.equals(String.valueOf(no)));
			
				if(!ad.equals(String.valueOf(no)) && ad != null)
				{
					for(Address address : addresses)
						if(address.getLine2() != null)
						{
							if(ad.equals(address.getLine1() + " " + address.getLine2() + " "  + address.getCity() + " "  + address.getProvince()))
								order.setAddressID(address.getAddressID());
						}
						else
						{
							if(ad.equals(address.getLine1() + " "  + address.getCity() + " "  + address.getProvince()))
								order.setAddressID(address.getAddressID());
						}
							
					
					Card card = new Card();
					card.setUserID(account.getId());
					ArrayList<Card> cards = card.getCardsOfUser();
					
					for(Card c : cards)
						view.getCards().addItem(String.valueOf(c.getCardNumber()));
					
					BankAccount bA = new BankAccount();
					bA.setUserID(account.getId());
					ArrayList<BankAccount> accounts = bA.getAccountsOfUser();
					
					for(BankAccount bAcc : accounts)
						view.getAccounts().addItem(bAcc.getBank() + " " + bAcc.getAccountNumber());
					
					String pay;
					
					if(!cards.isEmpty() || !accounts.isEmpty())
					{
						do {
							pay = view.selectPayment();
						}while(pay == null && !pay.equals(String.valueOf(no)));
						
						if(!pay.equals(String.valueOf(no)) && pay != null)
						{
							String answer = "";
							if(view.getBank().isSelected())
							{
								for(BankAccount bAcc : accounts)
									if(pay.equals(bAcc.getBank() + " " + bAcc.getAccountNumber()))
										order.setBankID(bAcc.getbAID());
							}
							else
							{
								for(Card c : cards)
									if(pay.equals(String.valueOf(c.getCardNumber())))
									{
										if(c.getCvc() == false)
											answer = view.getCVC();
										order.setCardID(c.getCardID());
									}
										
							}
							if(view.getCard().isSelected() && answer.equals(String.valueOf(no))) return;
							double coins = account.getCoinsOfUser();
							Cart c = new Cart();
							c.setUserID(account.getId());
							ArrayList<Cart> cart = c.getCartOfUser();
							boolean createdorder = false;
							for(Cart cc : cart)
							{
								if(cc.getQuantity() > cc.getProductStock(cc.getProductID()))
								{
									String result = view.toggleStockError(cc.getQuantity(), cc.getProductStock(cc.getProductID()), cc.getName());
									if(result.equals(String.valueOf(yes)))
									{
										cc.updateQuantity(cc.getProductStock(cc.getProductID()));
										
										update();
										if(!createdorder)
										{
											order.setId(order.createOrder(account.getId()));
											createdorder = true;
											
										}
										order.addtoOrder(cc.getProductID(), cc.getQuantity(), cc.getTotal());
										c.addIncomeToSeller(cc.getTotal() + c.getSellerIncome(cc.getProductID()), cc.getProductID());
										coins += (cc.getTotal()/500);
										cc.delete();
										order.subtractStock(cc.getProductStock(cc.getProductID()) - cc.getQuantity(), cc.getProductSold(cc.getProductID()) + cc.getQuantity(), cc.getProductID());
										update();
									}
										
								}
								else
								{
									update();
									if(!createdorder)
									{
										order.setId(order.createOrder(account.getId()));
										createdorder = true;
									}
									
									order.addtoOrder(cc.getProductID(), cc.getQuantity(), cc.getTotal());
									c.addIncomeToSeller(cc.getTotal() + c.getSellerIncome(cc.getProductID()),cc.getProductID());
									coins += (cc.getTotal()/500);
									cc.delete();
									order.subtractStock(cc.getProductStock(cc.getProductID()) - cc.getQuantity(), cc.getProductSold(cc.getProductID()) + cc.getQuantity(), cc.getProductID());
									update();
								}
							}
							account.setCoinsOfUser(coins);
							order.setOrderTotalQuantity(order.getOrderQuantity());
						}
					}				
				}
			}
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
		double subtotal = 0;
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
				
				subtotal += cc.getTotal();
			}
		
		view.getSubtotal().setText("Subtotal: $" + subtotal);
	}
	
	public void close()
	{
		view.dispose();
		mainMenu.setCart(null);
	}

}
