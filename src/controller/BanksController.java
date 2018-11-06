package controller;

import java.awt.Color;
import java.util.ArrayList;

import model.BankAccount;
import model.BankTableModel;
import model.User;
import view.BankAccounts;
import view.PLACEHOLDER;

public class BanksController {
	
	private BankAccounts view;
	private BankTableModel bankTableModel;
	private SettingsController settings;
	private User account;
	
	public BanksController(BankAccounts view, User account, SettingsController settings)
	{
		this.view = view;
		this.account = account;
		this.settings = settings;
		bankTableModel = null;
		view.addController(this);
		update();
	}
	
	public void clear()
	{
		Color fg = Color.GRAY;
		
		view.getBank().setForeground(fg);
		view.getAccountNumber().setForeground(fg);
		
		view.getBank().setText(PLACEHOLDER.BANK.toString());
		view.getAccountNumber().setText(PLACEHOLDER.ACCNUM.toString());
		
		view.getAddPanel().setVisible(false);
		
		view.getAdd().setEnabled(true);
	}
	
	public void close()
	{
		view.dispose();
		settings.setBankview(null);
	}
	
	public void update()
	{
		BankAccount baccount = new BankAccount();
		baccount.setUserID(account.getId());
		ArrayList<BankAccount> accounts = baccount.getAccountsOfUser();
		
		if(bankTableModel == null)
			bankTableModel = new BankTableModel(accounts);
		else
			bankTableModel.setAccounts(accounts);
		
		
		for(int i = view.getModelBanksTable().getRowCount() - 1; i >= 0; i--)
			view.getModelBanksTable().removeRow(i);
		
		for(int i = 0; i < bankTableModel.getRowCount(); i++)
		{
			BankAccount b = bankTableModel.getAccountAt(i);
			Object[] row = new Object[] {b.getBank(), b.getAccountNumber()};
			
			view.getModelBanksTable().addRow(row);
		}
	}
}
