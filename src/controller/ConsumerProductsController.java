package controller;

import java.util.ArrayList;

import model.ConsumerProductTableModel;
import model.Product;
import model.User;
import view.ConsumerProductsView;
import view.PLACEHOLDER;
import view.RatingView;

public class ConsumerProductsController {
	
	private ConsumerProductsView view;
	private User account;
	private CustomerMainMenuController mainMenu;
	private ArrayList<RatingView> ratings;
	private ArrayList<String> productRatingName;
	private String whereClause;
	private ConsumerProductTableModel productsTableModel;

	public ConsumerProductsController(ConsumerProductsView view, User account,
			CustomerMainMenuController mainMenu) {
		this.view = view;
		this.mainMenu = mainMenu;
		this.account = account;
		whereClause = "";
		productsTableModel = null;
		ratings = new ArrayList<RatingView>();
		productRatingName = new ArrayList<String>();
		view.addController(this);
		update();
	}
	
	public void showRatings()
	{
		Product p = productsTableModel.getProductAt(view.getProductsTable().getSelectedRow());
		productRatingName.add(p.getName());
		RatingView ratingV = new RatingView(p.getName());
		ratings.add(ratingV);
		
		new RatingsController(ratingV,account, this, null, p);
	}
	
	public void rate(int rating, String desc)
	{
		Product p = productsTableModel.getProductAt(view.getProductsTable().getSelectedRow());
		
		if(desc.equals(PLACEHOLDER.RATE.toString()) || desc.trim().isEmpty())
			desc = null;
		
		p.rate(rating, desc, account.getId());
		
		update();
	}
	
	public String checkQuantityError(int quantity)
	{
		String error = "";
		
		if(quantity > productsTableModel.getProductAt(view.getProductsTable().getSelectedRow()).getStock())
			error += "You have set a number higher than available. Please select a lower number";
		
		return error;
	}
	
	public void addToCart(int quantity)
	{
		Product product = productsTableModel.getProductAt(view.getProductsTable().getSelectedRow());
		
		product.addToCart(account.getId(), quantity);
		update();
	}
	
	public void follow()
	{
		Product product = productsTableModel.getProductAt(view.getProductsTable().getSelectedRow());
		User user = new User();
		user.setId(account.getId());
		user.followAccount(product.getSellerID());
		
		update();
	}
	
	public void unfollow()
	{
		Product product = productsTableModel.getProductAt(view.getProductsTable().getSelectedRow());
		User user = new User();
		user.setId(account.getId());
		user.unfollowAccount(product.getSellerID());
		
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
		
		if(buyer.checkFollow(seller.getId()))
			view.getFollow().setText(PLACEHOLDER.UNFOLLOW.toString());
		else
			view.getFollow().setText(PLACEHOLDER.FOLLOW.toString());
		
		if(buyer.getId() == seller.getId())
		{
			view.getRightClick().removeAll();
			view.getRightClick().add(view.getExpand());
		}
		else
		{
			view.getRightClick().add(view.getExpand());
			view.getRightClick().add(view.getRate());
			view.getRightClick().add(view.getCart());
			view.getRightClick().add(view.getFave());
			view.getRightClick().add(view.getFollow());
		}
		
	}
	
	public void unfavorite()
	{
		Product product = productsTableModel.getProductAt(view.getProductsTable().getSelectedRow());
		
		product.unfavorite(account.getId());
		
		update();
	}
	
	public void favorite()
	{
		Product product = productsTableModel.getProductAt(view.getProductsTable().getSelectedRow());
		
		product.favorite(account.getId());
		
		update();
	}
	
	public void close()
	{
		view.dispose();
		mainMenu.setProducts(null);
		
		for(RatingView r : ratings) r.dispose();
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
				Object[] row = new Object[] {p.getName(), p.getCategory(), p.getBrand(), p.getSeller(),  p.getDescription(), p.getFavorites(), p.getRating(),
											p.getStock(), p.getPrice(), p.getDiscount(), p.getShipping(), p.getShippingduration() + " days"};
				
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

	public ArrayList<RatingView> getRatings() {
		return ratings;
	}

	public void setRatings(ArrayList<RatingView> ratings) {
		this.ratings = ratings;
	}

	public ArrayList<String> getProductRatingName() {
		return productRatingName;
	}

	public void setProductRatingName(ArrayList<String> productRatingName) {
		this.productRatingName = productRatingName;
	}
	
	

}
