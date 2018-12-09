package controller;

import java.util.ArrayList;

import model.Order;
import model.OrdersTableModel;
import model.User;
import view.OrderDetailView;
import view.Orders;
import view.PLACEHOLDER;

public class OrdersController {
	private Orders view;
	private User account;
	private SettingsController settings;
	private OrdersTableModel modelOrdersTable;
	private String orderClause;
	private ArrayList<Integer> orderDetailIDs;
	private ArrayList<OrderDetailView> orderDetailViews;
	
	public OrdersController(Orders ordersview, User account, SettingsController settingsController) {
		this.view = ordersview;
		this.account = account;
		this.settings = settingsController;
		modelOrdersTable = null;
		orderDetailIDs = new ArrayList<Integer>();
		orderDetailViews = new ArrayList<OrderDetailView>();
		view.addController(this);
		orderClause = "";
		update();
	}
	
	public void showDetails()
	{
		Order order = modelOrdersTable.getOrderAt(view.getOrdersTable().getSelectedRow());
		orderDetailIDs.add(order.getId());
		OrderDetailView oDV = new OrderDetailView(order.getId());
		orderDetailViews.add(oDV);
		
		new OrderDetailsController(oDV, this, order);
	}
	
	public void dispose()
	{
		view.dispose();
		settings.setOrdersview(null);
		
		for(OrderDetailView o : orderDetailViews) o.dispose();
	}
	
	public void update()
	{
		String filter = (String)view.getOrderFilter().getSelectedItem();
		
		if(filter.equals(PLACEHOLDER.NTO.toString()))
			orderClause = " ORDER BY C." + Order.COL_CREATION + " DESC";
		else if(filter.equals(PLACEHOLDER.OTN.toString()))
			orderClause = " ORDER BY C." + Order.COL_CREATION + " ASC";
		else if(filter.equals(PLACEHOLDER.QHTL.toString()))
			orderClause = " ORDER BY C." + Order.COL_QUANTITY + " DESC";
		else if(filter.equals(PLACEHOLDER.QLTH.toString()))
			orderClause = " ORDER BY C." + Order.COL_QUANTITY + " ASC";
		else
			orderClause = " ORDER BY C." + Order.COL_ID + " ASC";
		
		Order order = new Order();
		order.setUserID(account.getId());
		ArrayList<Order> orders = order.getOrdersOfUser(orderClause);
		
		if(modelOrdersTable == null)
			modelOrdersTable = new OrdersTableModel(orders);
		else
			modelOrdersTable.setOrders(orders);
		

		for(int i = view.getModelOrdersTable().getRowCount() - 1; i >= 0; i--)
			view.getModelOrdersTable().removeRow(i);
		
		for(int i = 0; i < modelOrdersTable.getRowCount(); i++)
		{
			Order o = modelOrdersTable.getOrderAt(i);
			Object[] row = new Object[] {o.getId(), o.getQuantity(), o.getAddress(), o.getPayment(), o.getOrderDate()};

			view.getModelOrdersTable().addRow(row);
		}
		
		view.getCoins().setText("Coins: " + account.getCoinsOfUser());
	}

	public ArrayList<OrderDetailView> getOrderDetailViews() {
		return orderDetailViews;
	}

	public void setOrderDetailViews(ArrayList<OrderDetailView> orderDetailViews) {
		this.orderDetailViews = orderDetailViews;
	}

	public ArrayList<Integer> getOrderDetailIDs() {
		return orderDetailIDs;
	}

	public void setOrderDetailIDs(ArrayList<Integer> orderDetailIDs) {
		this.orderDetailIDs = orderDetailIDs;
	}

}
