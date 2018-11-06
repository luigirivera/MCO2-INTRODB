package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class BankTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private List<BankAccount> accounts;
	
	public BankTableModel(List<BankAccount> accounts)
	{
		this.accounts = new ArrayList<BankAccount>(accounts);
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
		BankAccount account = accounts.get(row);
		
		switch(row)
		{
		case 0: return account.getBank();
		case 1: return account.getAccountNumber();
		default: return "??";
		}
		
	}
	
	public BankAccount getAccountAt(int row)
	{
		return accounts.get(row);
	}
	
	public List<BankAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<BankAccount> accounts) {
		this.accounts = accounts;
	}
}
