package model;

import java.util.ArrayList;

import services.AnalyticService;

public class Analytic {
	private String name;
	private int month1;
	private int month2;
	private int month3;
	
	private AnalyticService analyticservice;
	
	public Analytic()
	{
		analyticservice = new AnalyticService();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMonth1() {
		return month1;
	}
	public void setMonth1(int month1) {
		this.month1 = month1;
	}
	public int getMonth2() {
		return month2;
	}
	public void setMonth2(int month2) {
		this.month2 = month2;
	}
	public int getMonth3() {
		return month3;
	}
	public void setMonth3(int month3) {
		this.month3 = month3;
	}
	
	public ArrayList<Analytic> getAnalytics(String category, int[] months, int year)
	{
		return analyticservice.getAnalytics(category, months, year);
	}
}
