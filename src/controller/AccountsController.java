package controller;

import java.util.ArrayList;

import model.AccountsTableModel;
import model.User;
import view.Accounts;
import view.NewCorporateAccount;
import view.PLACEHOLDER;

public class AccountsController {
	private Accounts view;
	private CorporateMainMenuController mainMenu;
	private AccountsTableModel accountsTableModel;
	private NewCorporateAccount newCorp;
	private String whereClause;

	public AccountsController(Accounts view, CorporateMainMenuController mainMenu) {
		this.view = view;
		this.mainMenu = mainMenu;
		newCorp = null;
		accountsTableModel = null;
		view.addController(this);
		
		whereClause = "";
		update();
	}
	
	public void addCorporate()
	{
		if(newCorp == null)
		{
			newCorp = new NewCorporateAccount();
			new NewCorpController(newCorp, this);
		}
		else
		{
			newCorp.revalidate();
			newCorp.toFront();
			newCorp.repaint();
		}
	}
	
	public void setMenuItems()
	{
		User user = accountsTableModel.getAccountAt(view.getAccountsTable().getSelectedRow());
		
		view.getRightClick().removeAll();
		
		if(user.isLocked()) view.getRightClick().add(view.getUnlock());
		if(user.isForDeletion()) view.getRightClick().add(view.getDelete());
	}
	
	public void delete()
	{
		User user = accountsTableModel.getAccountAt(view.getAccountsTable().getSelectedRow());
		
		user.delete();
		
		update();
	}
	
	public void unlock()
	{
		User user = accountsTableModel.getAccountAt(view.getAccountsTable().getSelectedRow());
		user.setLocked(false);
		user.setTries(0);
		
		user.updateAccountLock();
		update();
	}
	
	public void update()
	{
		ArrayList<User> accounts = null;
		
		String filter = (String)view.getFilter().getSelectedItem();
		if(filter.equals(PLACEHOLDER.LOCKED.toString()))
			whereClause = " WHERE U." + User.COL_ISLOCKED + " = 1";
		else if(filter.equals(PLACEHOLDER.DELETION.toString()))
			whereClause = " WHERE C." + User.COL_FORDELETION + " = 1";
		else if(filter.equals(PLACEHOLDER.CORPORATE.toString()))
			accounts = new User().getCorporateAccounts();
		else if(filter.equals(PLACEHOLDER.CONSUMER.toString()))	
			accounts = new User().getConsumerAccounts();
		else
			whereClause = "";
		
		if(accounts == null)
			accounts = new User().getAccounts(whereClause);
		
		if(accountsTableModel == null)
			accountsTableModel = new AccountsTableModel(accounts);
		else
			accountsTableModel.setAccounts(accounts);
		
		for(int i = view.getModelAccountsTable().getRowCount() - 1; i >= 0; i--)
			view.getModelAccountsTable().removeRow(i);
		
		for(int i = 0; i < accountsTableModel.getRowCount(); i++)
		{
			User u = accountsTableModel.getAccountAt(i);
			Object[] row;
			if(u.getNumber() != 0)
				row = new Object[] {u.getUsername(), u.getNumber(), u.getEmail(), u.getRegister(), u.getLastLogin()};
			else
				row = new Object[] {u.getUsername(), null, u.getEmail(), u.getRegister(), u.getLastLogin()};
			
			view.getModelAccountsTable().addRow(row);
		}
	}
	
	public void close()
	{
		view.dispose();
		mainMenu.setAccounts(null);
	}

	public NewCorporateAccount getNewCorp() {
		return newCorp;
	}

	public void setNewCorp(NewCorporateAccount newCorp) {
		this.newCorp = newCorp;
	}
	
	

}
