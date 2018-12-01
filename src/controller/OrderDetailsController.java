package controller;

import java.util.ArrayList;

import model.Order;
import model.OrderContent;
import model.OrderDetailsTableModel;
import model.STATUS;
import view.OrderDetailView;
import view.PLACEHOLDER;

public class OrderDetailsController {

	private OrderDetailView view;
	private OrdersController orders;
	private OrderDetailsTableModel modelOrderTable;
	private Order order;
	private String status;
	public OrderDetailsController(OrderDetailView view, OrdersController orders, Order order) {
		this.view = view;
		this.orders = orders;
		this.order = order;
		modelOrderTable = null;
		status = "";
		view.addController(this);
		
		update();
	}
	
	public void dispose()
	{
		int index = orders.getOrderDetailViews().indexOf(view);	
		orders.getOrderDetailIDs().remove(index);
		orders.getOrderDetailViews().remove(index);
		view.dispose();
	}
	
	public void update()
	{
		double subtotal = 0;
		String filter = (String)view.getStatus().getSelectedItem();
		
		if(filter.equals(PLACEHOLDER.PENDING.toString()))
			status = " AND " + OrderContent.COL_STATUS + " = '" + STATUS.PENDING.toString() + "'";
		else if(filter.equals(PLACEHOLDER.ONTRANSIT.toString()))
			status = " AND " + OrderContent.COL_STATUS + " = '" + STATUS.ONTRANSIT.toString() + "'";
		else if(filter.equals(PLACEHOLDER.DELAYED.toString()))
			status = " AND " + OrderContent.COL_STATUS + " = '" + STATUS.DELAYED.toString() + "'";
		else if(filter.equals(PLACEHOLDER.DELIVERED.toString()))
			status = " AND " + OrderContent.COL_STATUS + " = '" + STATUS.DELIVERED.toString() + "'";
		else
			status = "";
		
		OrderContent oD = new OrderContent();
		oD.setOrderid(order.getId());
		
		ArrayList<OrderContent> details = oD.getOrderDetails(status);
		
		if(modelOrderTable == null)
			modelOrderTable = new OrderDetailsTableModel(details);
		else
			modelOrderTable.setDetails(details);
		
		for(int i = view.getModelDetailsTable().getRowCount() - 1; i >= 0; i--)
			view.getModelDetailsTable().removeRow(i);
		
		for(int i = 0; i < modelOrderTable.getRowCount(); i++)
		{
			OrderContent oc = modelOrderTable.getDetailAt(i);
			Object[] row = new Object[] {oc.getName(), oc.getCategory(), oc.getBrand(), oc.getSeller(), oc.getQuantity(), oc.getStatus(), oc.getDelivery(), oc.getTotal()};
			
			view.getModelDetailsTable().addRow(row);
		}
		
		details = oD.getOrderDetails("");
		
		for(OrderContent oc : details) subtotal += oc.getTotal();
		
		view.getSubtotal().setText("Subtotal: $" + subtotal);
	}

}
