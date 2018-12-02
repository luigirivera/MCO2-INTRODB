package controller;

import model.Product;
import model.User;
import view.PLACEHOLDER;
import view.ProductInfo;

public class ProductInfoController {
	
	private ProductInfo view;
	private User account;
	private Product product;
	private SellerProductsController sellerProducts;

	public ProductInfoController(ProductInfo view, User account, SellerProductsController sellerProducts, Product product)
	{
		this.view = view;
		this.account = account;
		this.sellerProducts = sellerProducts;
		this.product = product;
		view.addController(this);
	}
	
	public void updateProduct()
	{
		Product p = generateProduct();
		p.setProductID(product.getProductID());
		
		p.updateProduct();
		sellerProducts.update();
	}
	
	public boolean doesProductExist()
	{
		return !getExistError().isEmpty();
	}
	
	public void addProduct()
	{
		Product p = generateProduct();
		
		p.addProduct();
		
		sellerProducts.update();
	}
	
	public String getExistError()
	{
		
		String error = "";
		Product p = generateProduct();

		
		if(p.productExists())
			error += "Product already exists\n";
		
		return error;
	}
	
	public Product generateProduct()
	{
		Product p = new Product();
		p.setSellerID(account.getId());
		p.setName(view.getPName().getText());
		p.setCategory(view.getCategory().getText());
		p.setBrand(view.getBrand().getText());
		
		if(!view.getDescription().getText().trim().isEmpty() && !view.getDescription().getText().equals(PLACEHOLDER.DESCRIPTION.toString()))
			p.setDescription(view.getDescription().getText());
		else
			p.setDescription(null);
		p.setStock(Long.parseLong(view.getStock().getText()));
		p.setPrice(Double.parseDouble(view.getPrice().getText()));
		
		if(!view.getDiscount().getText().trim().isEmpty() && !view.getDiscount().getText().equals(PLACEHOLDER.DISCOUNT.toString()))
			p.setDiscount(Double.parseDouble(view.getDiscount().getText()));
		else
			p.setDiscount(0);
		
		p.setShipping(Double.parseDouble(view.getShipping().getText()));
		p.setShippingduration(view.getDuration().getSelectedIndex());
		
		return p;
	}
	
	public boolean areFieldsValid()
	{
		return getFieldsError().isEmpty();
	}
	
	public String getFieldsError()
	{
		String error = "";
		
		if(view.getPName().getText().trim().isEmpty() || view.getPName().getText().equals(PLACEHOLDER.NAME.toString()))
			error += "Please enter a product name\n";
		if(view.getCategory().getText().trim().isEmpty() || view.getCategory().getText().equals(PLACEHOLDER.CATEGORY.toString()))
			error += "Please enter a category name\n";
		if(view.getBrand().getText().trim().isEmpty() || view.getBrand().getText().equals(PLACEHOLDER.BRAND.toString()))
			error += "Please enter a brand name\n";
		if(view.getStock().getText().trim().isEmpty() || view.getStock().getText().equals(PLACEHOLDER.STOCK.toString()))
			error += "Please enter a stock amount\n";
		if(view.getPrice().getText().trim().isEmpty() || view.getPrice().getText().equals(PLACEHOLDER.PRICE.toString()))
			error += "Please enter a price amount\n";
		if(view.getShipping().getText().trim().isEmpty() || view.getShipping().getText().equals(PLACEHOLDER.SHIPPING.toString()))
			error += "Please enter a shipping cost\n";
		
		try {
			if(Long.parseLong(view.getStock().getText()) <= 0) throw new NumberFormatException();
		}catch(Exception e)
		{
			error += "Please enter a valid stock amount. A valid amount is a positive number that is not zero\n";
		}
		
		try {
			if(Double.parseDouble(view.getPrice().getText()) <= 0) throw new NumberFormatException();
		}catch(Exception e)
		{
			error += "Please enter a vaid price amount. A valid amount is a positive number that is not zero\n";
		}
		
		try {
			if(!view.getDiscount().getText().trim().isEmpty() && !view.getDiscount().getText().equals(PLACEHOLDER.DISCOUNT.toString()) &&
					Double.parseDouble(view.getDiscount().getText()) < 0)
				throw new NumberFormatException();
		}catch(Exception e)
		{
			error += "Please enter a valid discount rate. A valid rate is a positive number that is not zero\n";
		}
		
		try {
			if(Double.parseDouble(view.getShipping().getText()) < 0) throw new NumberFormatException();
		}catch(Exception e)
		{
			error += "Please enter a valid shipping cost. A valid cost is a positive number that is not zero\n";
		}
		
		return error;
	}
	
	public void dispose()
	{
		view.dispose();
		sellerProducts.setProductInfo(null);
	}
}
