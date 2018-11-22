package controller;

import java.util.ArrayList;

import model.ConsumerProductTableModel;
import model.CorporateProductsTableModel;
import model.Product;
import view.CorporateProductsView;
import view.PLACEHOLDER;

public class CorporateProductsController {
	private CorporateProductsView view;
	private CorporateMainMenuController mainMenu;
	private CorporateProductsTableModel productsTableModel;
	private String whereClause;
	public CorporateProductsController(CorporateProductsView view,
			CorporateMainMenuController mainMenu) {
		
		this.view = view;
		this.mainMenu = mainMenu;
		productsTableModel = null;
		view.addController(this);
		
		update();
	}
	
	public void close()
	{
		view.dispose();
		mainMenu.setProducts(null);
	}
	
	public void delete()
	{
		Product product = productsTableModel.getProductAt(view.getProductsTable().getSelectedRow());
		
		product.delete();
		update();
	}
	
	public void update()
	{
		whereClause = " WHERE ";
		boolean whereExists = false;
		String order = (String) view.getOrder().getSelectedItem();
		System.out.println(order);
		if(!(view.getCategory().getText().isEmpty() || view.getCategory().getText().equals(PLACEHOLDER.CATEGORY.toString())) ||
			!(view.getBrand().getText().isEmpty() || view.getBrand().getText().equals(PLACEHOLDER.BRAND.toString())) ||
			!(view.getLowPrice().getText().isEmpty() || view.getLowPrice().getText().equals(PLACEHOLDER.PRICELOW.toString())) ||
			!(view.getHighPrice().getText().isEmpty() || view.getHighPrice().getText().equals(PLACEHOLDER.PRICEHIGH.toString())) ||
			!(order.trim().isEmpty()))
		{
			if(!view.getCategory().getText().isEmpty() && !view.getCategory().getText().equals(PLACEHOLDER.CATEGORY.toString()))
			{
				if(whereExists) whereClause += " AND ";
				else whereExists = true;
				whereClause += Product.COL_CATEGORY + " = '" + view.getCategory().getText() + "'";
			}
				
			if(!view.getBrand().getText().isEmpty() && !view.getBrand().getText().equals(PLACEHOLDER.BRAND.toString()))
			{
				if(whereExists) whereClause += " AND ";
				else whereExists = true;
				whereClause += Product.COL_BRAND + " = '" + view.getBrand().getText() + "'";
			}
			
			if(!view.getLowPrice().getText().isEmpty() && !view.getLowPrice().getText().equals(PLACEHOLDER.PRICELOW.toString()))
			{
				if(whereExists) whereClause += " AND ";
				else whereExists = true;
				whereClause += Product.COL_PRICE + " >= " + Double.parseDouble(view.getLowPrice().getText());
			}
			
			if(!view.getHighPrice().getText().isEmpty() && !view.getHighPrice().getText().equals(PLACEHOLDER.PRICEHIGH.toString()))
			{
				if(whereExists) whereClause += " AND ";
				else whereExists = true;
				whereClause += Product.COL_PRICE + " <= " + Double.parseDouble(view.getHighPrice().getText());
			}
			
			if(!(order.trim().isEmpty()))
			{
				if(!whereExists) whereClause = "";
				if(order.equals(PLACEHOLDER.PRICEHTL.toString())) whereClause += " ORDER BY " + Product.COL_PRICE + " DESC ";
				else if(order.equals(PLACEHOLDER.PRICELTH.toString())) whereClause += " ORDER BY " + Product.COL_PRICE + " ASC ";
				else if(order.equals(PLACEHOLDER.NAMEATZ.toString())) whereClause += " ORDER BY " + Product.COL_NAME + " ASC ";
				else if(order.equals(PLACEHOLDER.NAMEZTA.toString())) whereClause += " ORDER BY " + Product.COL_NAME + " DESC ";
			}
				
		}
				
		else
			whereClause = "";
		
		ArrayList<Product> products = new Product().getProducts(whereClause);
		
		if(productsTableModel == null)
			productsTableModel = new CorporateProductsTableModel(products);
		else
			productsTableModel.setProducts(products);
		
		for(int i = view.getModelProductsTable().getRowCount() - 1; i >= 0; i--)
			view.getModelProductsTable().removeRow(i);
		
		for(int i = 0; i < productsTableModel.getRowCount(); i++)
		{
			Product p = productsTableModel.getProductAt(i);
			Object[] row = new Object[] {p.getName(), p.getCategory(), p.getBrand(), p.getSeller(),  p.getDescription(),
										p.getStock(), p.getSold(), p.getPrice(), p.getDiscount(), p.getShipping()};
			
			view.getModelProductsTable().addRow(row);
		}
	}

	public boolean validateFields() {
		return getFieldErrors().isEmpty();
	}

	public String getFieldErrors() {
		String error = "";
		boolean areNumbers = true;
		if(!view.getLowPrice().getText().isEmpty() && !view.getLowPrice().getText().equals(PLACEHOLDER.PRICELOW.toString()))
		{
			try {
				Double.parseDouble(view.getLowPrice().getText());
			}catch(Exception e) {
				error += "Please enter a valid low price value\n";
				areNumbers = false;
			}
		}
		else areNumbers = false;

		if(!view.getHighPrice().getText().isEmpty() && !view.getHighPrice().getText().equals(PLACEHOLDER.PRICEHIGH.toString()))
		{
			try {
				Double.parseDouble(view.getHighPrice().getText());
			}catch(Exception e) {
				error += "Please enter a valid high price value\n";
				areNumbers = false;
			}
		}
		else areNumbers = false;
		
		if(areNumbers && Double.parseDouble(view.getHighPrice().getText()) < Double.parseDouble(view.getLowPrice().getText()))
			error += "Please enter a value for high price is higher than low price\n";
		
		return error;
	}

}
