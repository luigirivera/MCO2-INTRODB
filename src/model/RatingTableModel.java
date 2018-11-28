package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class RatingTableModel extends DefaultTableModel{
	private static final long serialVersionUID = 1L;
	private List<Rating> ratings;
	
	public RatingTableModel(List<Rating> ratings)
	{
		this.ratings = new ArrayList<Rating>(ratings);
	}
	
	@Override
	public int getRowCount() {
		if(ratings != null)
			return ratings.size();
		else
			return 0;
	}
	
	@Override
	public int getColumnCount() {
		return 4;
	}
	
	@Override
	public Object getValueAt(int row, int col)
	{
		Rating rating = ratings.get(row);
		
		switch(row)
		{
		case 0: return rating.getBuyer();
		case 1: return rating.getRating();
		case 2: return rating.getComment();
		case 3: return rating.getRatingdate();
		default: return "??";
		}
		
	}
	
	public Rating getRatingAt(int row)
	{
		return ratings.get(row);
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	
}
