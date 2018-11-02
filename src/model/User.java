package model;

public class User {

	private int id;
	private String username;
	private String password;
	private String email;
	private GENDER gender;
	private long number;
	private double wallet;
	private double income;
	private int coins;
	private int followers;
	private int following;
	private boolean isLocked;
	private boolean forDeletion;
	private boolean isCorporate;
	
	public static final String TABLE = "accounts";
	public static final String COL_ID = "id";
	public static final String COL_USERNAME = "username";
	public static final String COL_PASSWORD = "password";
	public static final String COL_GENDER = "gender";
	public static final String COL_EMAIL = "email";
	public static final String COL_NUMBER = "number";
	public static final String COL_WALLET = "walletbalance";
	public static final String COL_INCOME = "income";
	public static final String COL_COINS = "shopeecoins";
	public static final String COL_ISLOCKED = "islocked";
	public static final String COL_FORDELETION = "fordeletion";
	public static final String COL_ISCORPORATE = "iscorporate";
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public GENDER getGender() {
		return gender;
	}
	public void setGender(GENDER gender) {
		this.gender = gender;
	}
	public int getFollowers() {
		return followers;
	}
	public void setFollowers(int followers) {
		this.followers = followers;
	}
	public int getFollowing() {
		return following;
	}
	public void setFollowing(int following) {
		this.following = following;
	}
	
	public boolean doesExist() {
		
	}
}
