package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class CorporateProductsTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private List<Product> products;
	
	public CorporateProductsTableModel(List<Product> products)
	{
		this.products = new ArrayList<Product>(products);
	}
	
	@Override
	public int getRowCount() {
		if(products != null)
			return products.size();
		else
			return 0;
	}
	
	@Override
	public int getColumnCount() {
		return 13;
	}
	
	@Override
	public Object getValueAt(int row, int col)
	{
		Product product = products.get(row);
		
		switch(row)
		{
		case 0: return product.getName();
		case 1: return product.getCategory();
		case 2: return product.getBrand();
		case 3: return product.getSeller();
		case 4: return product.getDescription();
		case 5: return product.getFavorites();
		case 6: return product.getRating();
		case 7: return product.getStock();
		case 8: return product.getSold();
		case 9: return product.getDiscount();
		case 10: return product.getPrice();
		case 11: return product.getShipping();
		case 12: return product.getShippingduration();
		default: return "??";
		}
		
	}
	
	public Product getProductAt(int row)
	{
		return products.get(row);
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	

}
