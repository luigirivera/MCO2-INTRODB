package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class OrderDetailsTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private List<OrderContent> details;
	
	public OrderDetailsTableModel(List<OrderContent> details)
	{
		this.details = new ArrayList<OrderContent>(details);
	}
	
	@Override
	public int getRowCount() {
		if(details != null)
			return details.size();
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
		OrderContent detail = details.get(row);
		
		switch(row)
		{
		case 0: return detail.getName();
		case 1: return detail.getCategory();
		case 2: return detail.getBrand();
		case 3: return detail.getSeller();
		case 4: return detail.getQuantity();
		case 5: return detail.getStatus();
		case 6: return detail.getDelivery();
		case 7: return detail.getTotal();

		default: return "??";
		}
		
	}

	public OrderContent getDetailAt(int row)
	{
		return details.get(row);
	}
	public List<OrderContent> getDetails() {
		return details;
	}

	public void setDetails(List<OrderContent> details) {
		this.details = details;
	}
}
