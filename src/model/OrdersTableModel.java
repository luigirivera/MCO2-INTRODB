package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class OrdersTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private List<Order> orders;
	
	public OrdersTableModel(List<Order> orders)
	{
		this.orders = new ArrayList<Order>(orders);
	}
	
	@Override
	public int getRowCount() {
		if(orders != null)
			return orders.size();
		else
			return 0;
	}
	
	@Override
	public int getColumnCount() {
		return 5;
	}
	
	@Override
	public Object getValueAt(int row, int col)
	{
		Order order = orders.get(row);
		
		switch(row)
		{
		case 0: return order.getId();
		case 1: return order.getQuantity();
		case 2: return order.getAddress();
		case 3: return order.getPayment();
		case 4: return order.getOrderDate();

		default: return "??";
		}
		
	}
	
	public Order getOrderAt(int row)
	{
		return orders.get(row);
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	
}
