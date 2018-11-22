package controller;

import java.util.ArrayList;

import model.CorporateProductsTableModel;
import model.Product;
import view.CorporateProductsView;

public class CorporateProductsController {
	private CorporateProductsView view;
	private CorporateMainMenuController mainMenu;
	private CorporateProductsTableModel productsTableModel;
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
