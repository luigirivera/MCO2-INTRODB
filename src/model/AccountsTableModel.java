package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class AccountsTableModel extends DefaultTableModel{
	private static final long serialVersionUID = 1L;
	private List<User> accounts;
	
	public AccountsTableModel(List<User> accounts)
	{
		this.accounts = new ArrayList<User>(accounts);
	}
	
	@Override
	public int getRowCount() {
		if(accounts != null)
			return accounts.size();
		else
			return 0;
	}
	
	@Override
	public int getColumnCount() {
		return 5;
	}
	
	@Override
	public Object getValueAt(int row, int col)
	{
		User account = accounts.get(row);
		
		switch(row)
		{
		case 0: return account.getUsername();
		case 1: return account.getNumber();
		case 2: return account.getEmail();
		case 3: return account.getRegister();
		case 4: return account.getLastLogin();

		default: return "??";
		}
		
	}
	
	
	public User getAccountAt(int row)
	{
		return accounts.get(row);
	}

	public List<User> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<User> accounts) {
		this.accounts = accounts;
	}
	
	
}
