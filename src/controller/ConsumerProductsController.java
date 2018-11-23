package controller;

import java.util.ArrayList;

import driver.StopNShop;
import model.ConsumerProductTableModel;
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
	private ConsumerProductTableModel productsTableModel;

	public ConsumerProductsController(ConsumerProductsView view, StopNShop program, User account,
			CustomerMainMenuController mainMenu) {
		this.view = view;
		this.mainMenu = mainMenu;
		this.program = program;
		this.account = account;
		whereClause = "";
		productsTableModel = null;
		view.addController(this);
		update();
	}
	
	public void checkItems()
	{
		Product product = productsTableModel.getProductAt(view.getProductsTable().getSelectedRow());
		User buyer = account;
		User seller = new User();
		
		seller.setId(product.getSellerID());
		
		if(product.checkFavorite(buyer.getId()))
			view.getFave().setText(PLACEHOLDER.UNFAVE.toString());
		else
			view.getFave().setText(PLACEHOLDER.FAVE.toString());
	}
	
	public void unfavorite()
	{
		Product product = productsTableModel.getProductAt(view.getProductsTable().getSelectedRow());
		
		product.unfavorite(account.getId());
	}
	
	public void favorite()
	{
		Product product = productsTableModel.getProductAt(view.getProductsTable().getSelectedRow());
		
		product.favorite(account.getId());
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
				whereClause += "P." + Product.COL_CATEGORY + " = '" + view.getCategory().getText() + "'";
			}
				
			if(!view.getBrand().getText().isEmpty() && !view.getBrand().getText().equals(PLACEHOLDER.BRAND.toString()))
			{
				if(whereExists) whereClause += " AND ";
				else whereExists = true;
				whereClause += "P." + Product.COL_BRAND + " = '" + view.getBrand().getText() + "'";
			}
			
			if(!view.getLowPrice().getText().isEmpty() && !view.getLowPrice().getText().equals(PLACEHOLDER.PRICELOW.toString()))
			{
				if(whereExists) whereClause += " AND ";
				else whereExists = true;
				whereClause += "P." + Product.COL_PRICE + " >= " + Double.parseDouble(view.getLowPrice().getText());
			}
			
			if(!view.getHighPrice().getText().isEmpty() && !view.getHighPrice().getText().equals(PLACEHOLDER.PRICEHIGH.toString()))
			{
				if(whereExists) whereClause += " AND ";
				else whereExists = true;
				whereClause += "P." + Product.COL_PRICE + " <= " + Double.parseDouble(view.getHighPrice().getText());
			}
			
			if(!(order.trim().isEmpty()))
			{
				if(!whereExists) whereClause = "";
				if(order.equals(PLACEHOLDER.PRICEHTL.toString())) whereClause += " ORDER BY P." + Product.COL_PRICE + " DESC ";
				else if(order.equals(PLACEHOLDER.PRICELTH.toString())) whereClause += " ORDER BY P." + Product.COL_PRICE + " ASC ";
				else if(order.equals(PLACEHOLDER.NAMEATZ.toString())) whereClause += " ORDER BY P." + Product.COL_NAME + " ASC ";
				else if(order.equals(PLACEHOLDER.NAMEZTA.toString())) whereClause += " ORDER BY P." + Product.COL_NAME + " DESC ";
			}
				
		}
				
		else
			whereClause = "";
		
		ArrayList<Product> products = new Product().getProducts(whereClause);
		
		if(productsTableModel == null)
			productsTableModel = new ConsumerProductTableModel(products);
		else
			productsTableModel.setProducts(products);
		
		for(int i = view.getModelProductsTable().getRowCount() - 1; i >= 0; i--)
			view.getModelProductsTable().removeRow(i);
		
		if(productsTableModel.getRowCount() > 0 && productsTableModel.getProductAt(0).getName() != null)
			for(int i = 0; i < productsTableModel.getRowCount(); i++)
			{
				Product p = productsTableModel.getProductAt(i);
				Object[] row = new Object[] {p.getName(), p.getCategory(), p.getBrand(), p.getSeller(),  p.getDescription(), p.getFavorites(),
											p.getStock(), p.getPrice(), p.getDiscount(), p.getShipping()};
				
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
