package controller;

import java.util.ArrayList;

import driver.StopNShop;
import model.CorporateProductsTableModel;
import model.Product;
import model.User;
import view.CorporateProductsView;

public class CorporateProductsController {
	private CorporateProductsView view;
	private User account;
	private StopNShop program;
	private CorporateMainMenuController mainMenu;
	private CorporateProductsTableModel productsTableModel;
	public CorporateProductsController(CorporateProductsView view, User account, StopNShop program,
			CorporateMainMenuController mainMenu) {
		
		this.view = view;
		this.account = account;
		this.program = program;
		this.mainMenu = mainMenu;
		productsTableModel = null;
		view.addController(this);
		
		update();
	}
	
	public void update()
	{
		Product product = new Product();
		ArrayList<Product> products = product.getAllProducts();
		
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

}
