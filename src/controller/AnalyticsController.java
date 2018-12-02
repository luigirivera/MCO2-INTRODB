package controller;

import java.util.ArrayList;

import model.Analytic;
import model.AnalyticsTableModel;
import view.AnalyticsView;

public class AnalyticsController {
	private AnalyticsView view;
	private AnalyticsTableModel modelAnalyticsTable;
	private CorporateProductsController products;
	
	public AnalyticsController(AnalyticsView view, CorporateProductsController products) {
		this.view = view;
		this.products = products;
		modelAnalyticsTable = null;
		view.addController(this);
	}
	
	public void dispose()
	{
		view.dispose();
		products.setAnalytics(null);
	}
	
	public void update()
	{
		String quarter = (String)view.getQuarter().getSelectedItem();
		String[] months;
		int[] monthsint;
		if(quarter.equals("Q1"))
		{
			months = new String[] {"January", "February", "March"};
			monthsint = new int[] {1,2,3};
		}
			
		else if(quarter.equals("Q2"))
		{
			months = new String[] {"April", "May", "June"};
			monthsint = new int[] {4,5,6};
		}
		else if(quarter.equals("Q3"))
		{
			months = new String[] {"July", "August", "September"};
			monthsint = new int[] {7,8,9};
		}
			
		else
		{
			months = new String[] {"October", "November", "December"};
			monthsint = new int[] {10,11,12};
		}
		
		int year = (Integer)view.getYear().getSelectedItem();
		
		ArrayList<Analytic> analytics = new Analytic().getAnalytics(view.getCategory().getText(), monthsint, year);
		
		view.getModelAnalyticsTable().setColumnCount(1);
		view.getModelAnalyticsTable().addColumn(months[0]);
		view.getModelAnalyticsTable().addColumn(months[1]);
		view.getModelAnalyticsTable().addColumn(months[2]);
		
		if(modelAnalyticsTable == null)
			modelAnalyticsTable = new AnalyticsTableModel(analytics);
		else
			modelAnalyticsTable.setAnalytics(analytics);
		
		for(int i = view.getModelAnalyticsTable().getRowCount() - 1; i >= 0; i--)
			view.getModelAnalyticsTable().removeRow(i);
		
		for(int i = 0; i < modelAnalyticsTable.getRowCount(); i++)
		{
			Analytic a = modelAnalyticsTable.getAnalyticAt(i);
			Object[] row = new Object[] {a.getName(), a.getMonth1(), a.getMonth2(), a.getMonth3()};
			
			view.getModelAnalyticsTable().addRow(row);
		}
	}

}
