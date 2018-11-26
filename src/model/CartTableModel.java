package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class CartTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private List<Cart> cart;
	
	public CartTableModel(List<Cart> cart)
	{
		this.cart = new ArrayList<Cart>(cart);
	}
	
	@Override
	public int getRowCount() {
		if(cart != null)
			return cart.size();
		else
			return 0;
	}
	
	@Override
	public int getColumnCount() {
		return 6;
	}
	
	@Override
	public Object getValueAt(int row, int col)
	{
		Cart c = cart.get(row);
		
		switch(row)
		{
		case 0: return c.getName();
		case 1: return c.getQuantity();
		case 2: return c.getDiscount();
		case 3: return c.getPrice();
		case 4: return c.getShipping();
		case 5: return c.getTotal();
		default: return "??";
		}
		
	}
	
	public Cart getCartAt(int row)
	{
		return cart.get(row);
	}

	public List<Cart> getCart() {
		return cart;
	}

	public void setCart(List<Cart> cart) {
		this.cart = cart;
	}
	
	
}
