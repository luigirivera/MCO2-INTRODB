package controller;

import driver.StopNShop;
import model.Product;
import model.User;
import view.ConsumerProductsView;
import view.PLACEHOLDER;

public class ConsumerProductsController {
	
	private ConsumerProductsView view;
	private StopNShop program;
	private User account;
	private CustomerMainMenuController mainMenu;
	private String whereClause;

	public ConsumerProductsController(ConsumerProductsView view, StopNShop program, User account,
			CustomerMainMenuController mainMenu) {
		this.view = view;
		this.mainMenu = mainMenu;
		this.program = program;
		this.account = account;
		whereClause = "";
		view.addController(this);
		update();
	}
	
	public void close()
	{
		view.dispose();
		mainMenu.setProducts(null);
	}
	
	public void update()
	{
		whereClause = " WHERE ";
		boolean whereExists = false;
		String order = (String) view.getOrder().getSelectedItem();
		
		if(!(view.getCategory().getText().isEmpty() && view.getCategory().getText().equals(PLACEHOLDER.CATEGORY.toString())) ||
			!(view.getBrand().getText().isEmpty() && view.getBrand().getText().equals(PLACEHOLDER.BRAND.toString())) ||
			!(view.getLowPrice().getText().isEmpty() && view.getLowPrice().getText().equals(PLACEHOLDER.PRICELOW.toString())) ||
			!(view.getHighPrice().getText().isEmpty() && view.getHighPrice().getText().equals(PLACEHOLDER.PRICEHIGH.toString())) ||
			!(order.trim().isEmpty()))
		{
			if(!(view.getCategory().getText().isEmpty() && view.getCategory().getText().equals(PLACEHOLDER.CATEGORY.toString())))
			{
				if(whereExists) whereClause += " AND ";
				else whereExists = true;
				whereClause += Product.COL_CATEGORY + " = " + view.getCategory().getText();
			}
				
			if(!(view.getBrand().getText().isEmpty() && view.getBrand().getText().equals(PLACEHOLDER.BRAND.toString())))
			{
				if(whereExists) whereClause += " AND ";
				else whereExists = true;
				whereClause += Product.COL_BRAND + " = " + view.getBrand().getText();
			}
			
			if(!(view.getLowPrice().getText().isEmpty() && view.getLowPrice().getText().equals(PLACEHOLDER.PRICELOW.toString())))
			{
				if(whereExists) whereClause += " AND ";
				else whereExists = true;
				whereClause += Product.COL_PRICE + " >= " + Double.parseDouble(view.getLowPrice().getText());
			}
			
			if(!(view.getHighPrice().getText().isEmpty() && view.getHighPrice().getText().equals(PLACEHOLDER.PRICEHIGH.toString())))
			{
				if(whereExists) whereClause += " AND ";
				else whereExists = true;
				whereClause += Product.COL_PRICE + " <= " + Double.parseDouble(view.getHighPrice().getText());
			}
			
			if(!(order.trim().isEmpty()))
			{
				
				if(order.equals(PLACEHOLDER.PRICEHTL.toString())) whereClause += " ORDER BY " + Product.COL_PRICE + " DESC ";
				else if(order.equals(PLACEHOLDER.PRICELTH.toString())) whereClause += " ORDER BY " + Product.COL_PRICE + " ASC ";
				else if(order.equals(PLACEHOLDER.NAMEATZ.toString())) whereClause += " ORDER BY " + Product.COL_NAME + " DESC ";
				else if(order.equals(PLACEHOLDER.NAMEZTA.toString())) whereClause += " ORDER BY " + Product.COL_NAME + " ASC ";
			}
				
		}
				
		else
			whereClause = "";
	}

	public boolean validateFields() {
		return getFieldErrors().isEmpty();
	}

	public String getFieldErrors() {
		String error = "";
		
		try {
			Double.parseDouble(view.getLowPrice().getText());
		}catch(Exception e) {
			error += "Please enter a valid low price value\n";
		}
		
		try {
			Double.parseDouble(view.getHighPrice().getText());
		}catch(Exception e) {
			error += "Please enter a valid high price value\n";
		}
		
		return error;
	}

}
