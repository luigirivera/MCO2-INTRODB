package controller;

import java.util.ArrayList;

import model.Order;
import model.OrderContent;
import model.SalesTableModel;
import model.User;
import view.PLACEHOLDER;
import view.SalesView;

public class SalesController {
	private SalesView view;
	private User account;
	private SellerPortalController portal;
	private SalesTableModel modelSalesTable;
	private String whereClause;
	
	public SalesController(SalesView view, User account, SellerPortalController portal) {
		this.view = view;
		this.account = account;
		this.portal = portal;
		modelSalesTable = null;
		whereClause = "";
		view.addController(this);
		update();
	}
	
	public void updateStatus(String status)
	{
		OrderContent details = modelSalesTable.getSaleAt(view.getSalesTable().getSelectedRow());
		
		details.updateStatus(status);
		update();
	}
	
	public void dispose()
	{
		view.dispose();
		portal.setSales(null);
	}
	
	public void update()
	{
		String filter = (String)view.getFilter().getSelectedItem();
		
		if(filter.equals(PLACEHOLDER.THISMONTH.toString()))
			whereClause = " AND MONTH(O." + Order.COL_CREATION + ") = MONTH(NOW())";
		else if(filter.equals(PLACEHOLDER.LASTMONTH.toString()))
			whereClause = " AND MONTH(O." + Order.COL_CREATION + ") = MONTH(DATE_SUB(NOW(), INTERVAL 1 MONTH))";
		else if(filter.equals(PLACEHOLDER.FIVERECENT.toString()))
			whereClause = " ORDER BY O." + Order.COL_CREATION + " DESC LIMIT 5";
		else if(filter.equals(PLACEHOLDER.TENRECENT.toString()))
			whereClause = " ORDER BY O." + Order.COL_CREATION + " DESC LIMIT 10";
		else if(filter.equals(PLACEHOLDER.FIVEOLDEST.toString()))
			whereClause = " ORDER BY O." + Order.COL_CREATION + " ASC LIMIT 5";
		else if(filter.equals(PLACEHOLDER.TENOLDEST.toString()))
			whereClause = " ORDER BY O." + Order.COL_CREATION + " ASC LIMIT 10";
		else
			whereClause = " ORDER BY O." + Order.COL_CREATION + " DESC";
		
		ArrayList<OrderContent> details = new OrderContent().getSalesOfSeller(account.getId(), whereClause);
		
		if(modelSalesTable == null)
			modelSalesTable = new SalesTableModel(details);
		else
			modelSalesTable.setSales(details);
	
		for(int i = view.getModelSalesTable().getRowCount() - 1; i >= 0; i--)
			view.getModelSalesTable().removeRow(i);
		
		for(int i = 0; i < modelSalesTable.getRowCount(); i++)
		{
			OrderContent oc = modelSalesTable.getSaleAt(i);
			Object[] row = new Object[] {oc.getName(), oc.getCategory(), oc.getBrand(), oc.getBuyer(), oc.getQuantity(), oc.getStatus(), oc.getOrderdate(), oc.getTotal()};
			
			view.getModelSalesTable().addRow(row);
		}
	}

}
