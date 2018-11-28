package controller;

import java.util.ArrayList;

import driver.StopNShop;
import model.Product;
import model.Rating;
import model.RatingTableModel;
import model.User;
import view.RatingView;

public class RatingsController {
	
	private RatingView view;
	private StopNShop program;
	private User account;
	private RatingTableModel modelRatingTable;
	private ConsumerProductsController products;
	private Product product;
	
	public RatingsController(RatingView view, StopNShop program, User account,
			ConsumerProductsController products, Product product) {
		this.view = view;
		this.products = products;
		this.program = program;
		this.account = account;
		this.product = product;
		modelRatingTable = null;
		view.addController(this);
		update();
	}
	
	public void close()
	{
		int index = products.getRatings().indexOf(this);
		
		products.getRatings().remove(index);
		products.getProductRatingName().remove(index);
		
		view.dispose();
	}
	
	public void deleteRating()
	{
		Rating r = modelRatingTable.getRatingAt(view.getRatingsTable().getSelectedRow());
		r.delete();
		update();
		products.update();
	}
	
	public void checkItems()
	{
		Rating r = modelRatingTable.getRatingAt(view.getRatingsTable().getSelectedRow());
		
		view.getRightClick().removeAll();
		if(r.getUser() == account.getId())
			view.getRightClick().add(view.getDelete());
	}
	
	private void update()
	{
		ArrayList<Rating> ratings = new Product().getRatings(product.getProductID());
		
		if(modelRatingTable == null)
			modelRatingTable = new RatingTableModel(ratings);
		else
			modelRatingTable.setRatings(ratings);
		
		for(int i = view.getModelRatingsTable().getRowCount() - 1; i >= 0; i--)
			view.getModelRatingsTable().removeRow(i);
		
		for(int i = 0; i < modelRatingTable.getRowCount(); i++)
		{
			Rating r = modelRatingTable.getRatingAt(i);
			Object[] row = new Object[] {r.getBuyer(), r.getRating(), r.getComment(), r.getRatingdate()};
			
			view.getModelRatingsTable().addRow(row);
		}
	}

}
