package model;

import java.util.ArrayList;

import services.AddressService;

public class Address {
	private int addressID;
	private int userID;
	private String line1;
	private String line2;
	private String city;
	private String province;
	private int zip;
	
	private AddressService addressservice;
	
	public static final String TABLE = "address";
	public static final String COL_ADDRESSID = "addressid";
	public static final String COL_USERID = "userid";
	public static final String COL_LINE1 = "line1";
	public static final String COL_LINE2 = "line2";
	public static final String COL_CITY = "city";
	public static final String COL_PROV = "province";
	public static final String COL_ZIP = "zipcode";
	
	public Address()
	{
		addressservice = new AddressService();
	}
	
	public int getAddressID() {
		return addressID;
	}

	public void setAddressID(int addressID) {
		this.addressID = addressID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public int getZip() {
		return zip;
	}
	public void setZip(int zip) {
		this.zip = zip;
	}
	
	public void saveAddress()
	{
		addressservice.saveAddress(userID, line1, line2, city, province, zip);
	}
	
	public boolean doesAddressExist()
	{
		return addressservice.doesAddressExist(userID, line1, line2, city, province, zip);
	}
	
	public ArrayList<Address> getAddressesOfUser()
	{
		return addressservice.getAddressesOfUser(userID);
	}
	
	public void delete()
	{
		addressservice.delete(userID, line1, line2, city, province, zip);
	}
}
