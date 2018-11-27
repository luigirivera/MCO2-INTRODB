package controller;

import java.util.ArrayList;

import model.Product;
import model.SellerProductsTableModel;
import model.User;
import view.ProductInfo;
import view.SellerProducts;

public class SellerProductsController {
	private SellerProducts view;
	private ProductInfo productInfo;
	private User account;
	private Product product;
	private SellerPortalController portal;
	private SellerProductsTableModel productsTableModel;
	
	public SellerProductsController(SellerProducts view, User account, SellerPortalController portal)
	{
		this.view = view;
		this.account = account;
		this.portal = portal;
		productsTableModel = null;
		product = null;
		productInfo = null;
		view.addController(this);
		update();
	}
	
	public void getData()
	{
		product = productsTableModel.getProductAt(view.getProductsTable().getSelectedRow());
	}
	
	public void setData()
	{
		productInfo.setData(product.getName(), product.getCategory(), product.getBrand(), product.getDescription(), 
				String.valueOf(product.getStock()), String.valueOf(product.getPrice()), String.valueOf(product.getDiscount()), 
				String.valueOf(product.getShipping()));
	}
	
	public void toggleProductInfo()
	{
		if(productInfo == null)
		{
			productInfo = new ProductInfo();
			new ProductInfoController(productInfo, account, this, product);
		}
		else
		{
			productInfo.toFront();
			productInfo.revalidate();
			productInfo.repaint();
		}
	}
	
	public void delete()
	{
		Product product = productsTableModel.getProductAt(view.getProductsTable().getSelectedRow());
		
		product.delete();
		update();
	}
	
	public void close()
	{
		view.dispose();
		portal.setProducts(null);
	}
	
	public void update()
	{
		Product product = new Product();
		product.setSellerID(account.getId());
		ArrayList<Product> products = product.getProductsOfUser();
		
		if(productsTableModel == null)
			productsTableModel = new SellerProductsTableModel(products);
		else
			productsTableModel.setProducts(products);
		
		for(int i = view.getModelProductsTable().getRowCount() - 1; i >= 0; i--)
			view.getModelProductsTable().removeRow(i);
		
		if(productsTableModel.getRowCount() > 0 && productsTableModel.getProductAt(0).getName() != null)
			for(int i = 0; i < productsTableModel.getRowCount(); i++)
			{
				Product p = productsTableModel.getProductAt(i);
				Object[] row = new Object[] {p.getName(), p.getCategory(), p.getBrand(), p.getDescription(), p.getFavorites(), p.getRating(),
											p.getStock(), p.getSold(), p.getPrice(), p.getDiscount(), p.getShipping()};
				
				view.getModelProductsTable().addRow(row);
			}
	}

	public ProductInfo getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}
	
	
}
