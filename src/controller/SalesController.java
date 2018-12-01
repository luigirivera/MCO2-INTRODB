package controller;

import model.Order;
import model.User;
import view.PLACEHOLDER;
import view.SalesView;

public class SalesController {
	private SalesView view;
	private User account;
	private SellerPortalController portal;
	private String whereClause;
	public SalesController(SalesView view, User account, SellerPortalController portal) {
		this.view = view;
		this.account = account;
		this.portal = portal;
		whereClause = "";
		view.addController(this);
		update();
	}
	
	public void dispose()
	{
		view.dispose();
		portal.setSales(null);
	}
	
	public void update()
	{
		String filter = (String)view.getFilter().getSelectedItem();
		
		if(filter.equals(PLACEHOLDER.THISMONTH.toString()))
			whereClause = " AND MONTH(" + Order.COL_CREATION + ") = MONTH(NOW())";
		else if(filter.equals(PLACEHOLDER.LASTMONTH.toString()))
			whereClause = " AND MONTH(" + Order.COL_CREATION + ") = MONTH(DATE_SUB(NOW(), INTERVAL 1 MONTH)";
		else if(filter.equals(PLACEHOLDER.FIVERECENT.toString()))
			whereClause = " ORDER BY " + Order.COL_CREATION + " DESC LIMIT 5";
		else if(filter.equals(PLACEHOLDER.TENRECENT.toString()))
			whereClause = " ORDER BY " + Order.COL_CREATION + " DESC LIMIT 10";
		else if(filter.equals(PLACEHOLDER.FIVEOLDEST.toString()))
			whereClause = " ORDER BY " + Order.COL_CREATION + " ASC LIMIT 5";
		else if(filter.equals(PLACEHOLDER.TENOLDEST.toString()))
			whereClause = " ORDER BY " + Order.COL_CREATION + " ASC LIMIT 10";
		else
			whereClause = " ORDER BY " + Order.COL_CREATION + " DESC";
		
		/*SELECT P.name, P.category, P.brand, A.username, OC.quantity, OC.status, O.creationdate, OC.total 

FROM stopnshop.ordercontent AS OC, product AS P, account AS A, consumerorder AS O

WHERE OC.orderid = O.orderid AND OC.productID = P.id AND A.userid = O.userID;*/
	}

}
