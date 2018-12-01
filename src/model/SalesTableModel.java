package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class SalesTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private List<OrderContent> sales;
	
	public SalesTableModel(List<OrderContent> sales)
	{
		this.sales = new ArrayList<OrderContent>(sales);
	}
	
	@Override
	public int getRowCount() {
		if(sales != null)
			return sales.size();
		else
			return 0;
	}
	
	@Override
	public int getColumnCount() {
		return 8;
	}
	
	@Override
	public Object getValueAt(int row, int col)
	{
		OrderContent detail = sales.get(row);
		
		switch(row)
		{
		case 0: return detail.getName();
		case 1: return detail.getCategory();
		case 2: return detail.getBrand();
		case 3: return detail.getBuyer();
		case 4: return detail.getQuantity();
		case 5: return detail.getStatus();
		case 6: return detail.getOrderdate();
		case 7: return detail.getTotal();

		default: return "??";
		}
		
	}
	
	public OrderContent getSaleAt(int row)
	{
		return sales.get(row);
	}

	public List<OrderContent> getSales() {
		return sales;
	}

	public void setSales(List<OrderContent> sales) {
		this.sales = sales;
	}
	
	
}
