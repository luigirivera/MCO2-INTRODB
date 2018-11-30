package model;

import java.sql.Date;
import java.util.ArrayList;

import services.AccountsService;
import services.LoginService;
import services.SettingsService;

public class User {

	private int userID;
	private String username;
	private String password;
	private String email;
	private Date birthday;
	private String gender;
	private long number;
	private double wallet;
	private double income;
	private int coins;
	private int tries;
	private boolean isLocked;
	private boolean forDeletion;
	private boolean isCorporate;
	private Date register;
	private Date lastLogin;
	
	private LoginService loginservice;
	private SettingsService settingsservice;
	private AccountsService accountservice;
	
	public static final String TABLE = "account";
	public static final String CORP_TABLE = "corporate";
	public static final String CONSU_TABLE = "consumer";
	
	public static final String COL_ID = "userid";
	public static final String COL_USERNAME = "username";
	public static final String COL_PASSWORD = "password";
	public static final String COL_BIRTHDAY = "birthday";
	public static final String COL_GENDER = "gender";
	public static final String COL_EMAIL = "email";
	public static final String COL_NUMBER = "number";
	public static final String COL_WALLET = "walletbalance";
	public static final String COL_INCOME = "income";
	public static final String COL_COINS = "shopeecoins";
	public static final String COL_TRIES = "logintries";
	public static final String COL_ISLOCKED = "islocked";
	public static final String COL_FORDELETION = "fordeletion";
	public static final String COL_ISCORPORATE = "iscorporate";
	public static final String COL_REGISTER = "registerdate";
	public static final String COL_LASTLOGIN = "lastlogindate";
	
	public User() {
		loginservice = new LoginService();
		settingsservice = new SettingsService();
		accountservice = new AccountsService();
	}
	
	public int getId() {
		return userID;
	}
	public void setId(int id) {
		this.userID = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
	public double getWallet() {
		return wallet;
	}
	public void setWallet(double wallet) {
		this.wallet = wallet;
	}
	public double getIncome() {
		return income;
	}
	public void setIncome(double income) {
		this.income = income;
	}
	public int getCoins() {
		return coins;
	}
	public void setCoins(int coins) {
		this.coins = coins;
	}
	public boolean isLocked() {
		return isLocked;
	}
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	public boolean isForDeletion() {
		return forDeletion;
	}
	public void setForDeletion(boolean forDeletion) {
		this.forDeletion = forDeletion;
	}
	public boolean isCorporate() {
		return isCorporate;
	}
	public void setCorporate(boolean isCorporate) {
		this.isCorporate = isCorporate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public boolean doesUsernameExist() {
		return loginservice.doesUsernameExist(username);
	}
	
	public boolean doesEmailExist() {
		return loginservice.doesEmailExist(email);
	}
	
	public boolean doesNumberExist() {
		return loginservice.doesNumberExist(number);
	}
	
	
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getTries() {
		return tries;
	}

	public void setTries(int tries) {
		this.tries = tries;
	}

	public Date getRegister() {
		return register;
	}

	public void setRegister(Date register) {
		this.register = register;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public void registerAccount() {
		if(email != null)
			loginservice.registerAccount(username, password, email);
		else
			loginservice.registerAccount(username, password, number);
	}
	
	public User getAccountLogin()
	{
		return loginservice.getAccountLogin(username);
	}
	
	public void updateAccountLock()
	{
		loginservice.updateAccount(username, tries, isLocked);
	}
	
	public void updateInformation()
	{
		if(number == 0)
			settingsservice.updateInformation(userID, username,email, birthday, gender);
		else
			settingsservice.updateInformation(userID, username,email,number,birthday, gender);
	}
	
	public void changePassword()
	{
		settingsservice.changePassword(username, password);
	}
	
	public void updateDelete()
	{
		settingsservice.updateDelete(userID,forDeletion);
	}
	
	public void setLogin()
	{
		loginservice.setLogin(username);
	}
	
	public User getDetails()
	{
		return loginservice.getDetails(username);
	}
	
	public void followAccount(int account)
	{
		loginservice.followAccount(account, userID);
	}
	
	public int getuserID()
	{
		return loginservice.getID(username);
	}

	public ArrayList<User> getAccounts(String whereClause) {
		return accountservice.getAccounts(whereClause);
	}

	public void delete() {
		accountservice.deleteAccount(userID);
		
	}
	
	public boolean checkFollow(int account)
	{
		return accountservice.checkFollow(account, userID);
	}
	
	public void unfollowAccount(int account)
	{
		accountservice.unfollow(account, userID);
	}
	
	public boolean checkCorporate()
	{
		return loginservice.checkCorporate(userID);
	}

	public boolean getDeletion() {
		return settingsservice.getDeletion(userID);
	}
}
