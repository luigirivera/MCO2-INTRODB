package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class AnalyticsTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private List<Analytic> analytics;
	
	public AnalyticsTableModel(List<Analytic> analytics)
	{
		this.analytics = new ArrayList<Analytic>(analytics);
	}
	
	@Override
	public int getRowCount() {
		if(analytics != null)
			return analytics.size();
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
		Analytic analytic = analytics.get(row);
		
		switch(row)
		{
		case 0: return analytic.getName();
		case 1: return analytic.getMonth1();
		case 2: return analytic.getMonth2();
		case 3: return analytic.getMonth3();
		default: return "??";
		}
		
	}

	public Analytic getAnalyticAt(int row)
	{
		return analytics.get(row);
	}
	
	public List<Analytic> getAnalytics() {
		return analytics;
	}

	public void setAnalytics(List<Analytic> analytics) {
		this.analytics = analytics;
	}
	
	
}
