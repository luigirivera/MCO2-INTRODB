package model;

import java.util.ArrayList;

import services.BanksService;

public class BankAccount {
	private int bAID;
	private int userID;
	private String bank;
	private long accountNumber;
	
	private BanksService banksservice;
	
	public static final String TABLE = "bankaccount";
	public static final String COL_BAID = "accountid";
	public static final String COL_USERID = "userid";
	public static final String COL_BANK = "bank";
	public static final String COL_ACCNUM = "accountnumber";
	

	public BankAccount()
	{
		banksservice = new BanksService();
	}


	public int getbAID() {
		return bAID;
	}


	public void setbAID(int bAID) {
		this.bAID = bAID;
	}


	public int getUserID() {
		return userID;
	}


	public void setUserID(int userID) {
		this.userID = userID;
	}


	public String getBank() {
		return bank;
	}


	public void setBank(String bank) {
		this.bank = bank;
	}


	public long getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public ArrayList<BankAccount> getAccountsOfUser()
	{
		return banksservice.getAccountsOfUser(userID);
	}
	
	public void saveAccount()
	{
		banksservice.saveAccount(userID, bank, accountNumber);
	}
	
	public boolean doesAccountExist()
	{
		return banksservice.doesAccountExist(userID, bank, accountNumber);
	}
	
	public void delete()
	{
		banksservice.delete(userID, bAID);
	}
}
