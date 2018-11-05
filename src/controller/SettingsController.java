package controller;

import java.awt.Color;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import driver.StopNShop;
import model.GENDER;
import model.User;
import view.Addresses;
import view.BankAccounts;
import view.CreditCards;
import view.Orders;
import view.PLACEHOLDER;
import view.Settings;

public class SettingsController {
	private Settings view;
	private Addresses addressview;
	private BankAccounts bankview;
	private CreditCards cardsview;
	private Orders ordersview;
	private StopNShop program;
	private User account;
	
	public SettingsController(Settings view, StopNShop program, User account) {
		this.view = view;
		addressview = null;
		bankview = null;
		cardsview = null;
		ordersview = null;
		this.program = program;
		this.account = account;
		view.addController(this);
		configureFields();
	}
	
	public void clear()
	{
		if(addressview!=null)
			addressview.dispose();
		if(bankview != null)
			bankview.dispose();
		if(cardsview != null)
			cardsview.dispose();
		if(ordersview != null)
			ordersview.dispose();
	}
	
	public void showAddresses()
	{
		if(addressview == null)
		{
			addressview = new Addresses();
		}
		else
		{
			addressview.toFront();
			addressview.repaint();
		}
		
	}
	
	public void showBanks()
	{
		if(bankview == null)
		{
			bankview = new BankAccounts();
		}
		else
		{
			bankview.toFront();
			bankview.repaint();
		}
		
	}
	
	public void showCards()
	{
		if(cardsview == null)
		{
			cardsview = new CreditCards();
			new CreditCardsController(cardsview, program, account, this);
		}
		else
		{
			cardsview.toFront();
			cardsview.repaint();
		}
		
	}
	
	public void showOrders()
	{
		if(ordersview == null)
		{
			ordersview = new Orders();
		}
		else
		{
			ordersview.toFront();
			ordersview.repaint();
		}
		
	}
	
	public boolean accountForDeletion()
	{
		return account.isForDeletion();
	}
	
	public void updateDelete(boolean deletion)
	{
		account.setForDeletion(deletion);
		account.updateDelete();
		
		program.setAccount(account.getDetails());
		account = account.getDetails();
		System.out.println(account.isForDeletion());
		if(deletion)
			view.getDelete().setText("Cancel Deletion");
		else
			view.getDelete().setText("Delete?");
	}
	
	public void changePassword()
	{
		account.setPassword(String.valueOf(view.getNewpass().getPassword()));
		
		account.changePassword();
		
		program.setAccount(account.getDetails());
		account = account.getDetails();
	}
	
	public void clearPassFields()
	{
		view.getOldpass().setText(PLACEHOLDER.PASSWORD.toString());
		view.getNewpass().setText(PLACEHOLDER.PASSWORD.toString());
		view.getConfirmpass().setText(PLACEHOLDER.PASSWORD.toString());
		
		Color fg = Color.GRAY;
		
		view.getOldpass().setForeground(fg);
		view.getNewpass().setForeground(fg);
		view.getConfirmpass().setForeground(fg);
	}
	
	public boolean validatePassword()
	{
		return getPasswordErrors().isEmpty();
	}
	
	public String getPasswordErrors()
	{
		String error = "";
		String old = String.valueOf(view.getOldpass().getPassword());
		String newP = String.valueOf(view.getNewpass().getPassword());
		String confirm = String.valueOf(view.getConfirmpass().getPassword());
		String current = account.getPassword();
		
		if(old.isEmpty() || newP.isEmpty() || confirm.isEmpty() ||
				String.valueOf(view.getOldpass().getPassword()).equals(PLACEHOLDER.PASSWORD.toString()) ||
				String.valueOf(view.getNewpass().getPassword()).equals(PLACEHOLDER.PASSWORD.toString()) ||
				String.valueOf(view.getConfirmpass().getPassword()).equals(PLACEHOLDER.PASSWORD.toString()))
		{
			if(old.isEmpty() ||
					String.valueOf(view.getOldpass().getPassword()).equals(PLACEHOLDER.PASSWORD.toString()))
				error += "Current password field is empty\n";
			if(newP.isEmpty() ||
					String.valueOf(view.getNewpass().getPassword()).equals(PLACEHOLDER.PASSWORD.toString()))
				error += "New password field is empty\n";
			if(confirm.isEmpty() ||
					String.valueOf(view.getConfirmpass().getPassword()).equals(PLACEHOLDER.PASSWORD.toString()))
				error += "Confirm password field is empty\n";
		}
		else if(old.equals(current))
		{

			
			if(newP.length() >= 6)
			{
				if(!newP.equals(confirm))
					error += "Passwords do not match\n";
			}
				
			else
				error += "Password needs to be at least 6 characters\n";
					
		}
		else
			error += "Incorrect current password\n";
			
		
		return error;
		
	}
	
	public void updateInformation()
	{
		User user = generateUser();
		user.setId(account.getId());
		user.updateInformation();
		program.setAccount(user.getDetails());
		account = user.getDetails();
	}
	
	public boolean checkFields()
	{
		return getErrors().isEmpty();
	}
	
	public User generateUser()
	{
		User user = new User();
		
		if(view.getUsername().getText().equals(PLACEHOLDER.USERNAME.toString()) || view.getUsername().getText().trim().isEmpty()) 
			user.setUsername(null);
		else
			user.setUsername(view.getUsername().getText());
		
		if(view.getEmail().getText().equals(PLACEHOLDER.EMAIL.toString()) || view.getEmail().getText().trim().isEmpty())
			user.setEmail(null);
		else
			user.setEmail(view.getEmail().getText());
		
		try {
			if(view.getNumber().getText().equals(PLACEHOLDER.NUMBER.toString()) || view.getNumber().getText().trim().isEmpty())
				user.setNumber(0);
			else
				user.setNumber(Long.parseLong(view.getNumber().getText()));
		}catch(Exception e)
		{
			user.setNumber(0);
		}
		
		
		if(view.getMale().isSelected())
			user.setGender(GENDER.M.toString());
		else if(view.getFemale().isSelected())
			user.setGender(GENDER.F.toString());
		else
			user.setGender(null);
		
		if(view.getMonth().getSelectedIndex() == -1 || view.getDate().getSelectedIndex() == -1 || view.getYear().getSelectedIndex() == -1)
			user.setBirthday(null);
		
		
		else
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			try {
				Date date = new Date(sdf.parse(view.getYear().getItemAt(view.getYear().getSelectedIndex()) +"/" +
															view.getMonth().getSelectedIndex() + "/"+
															(((Integer)view.getDate().getSelectedItem()) + 1)).getTime());
				user.setBirthday(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			
		}
		
		return user;
	}
	
	public String getErrors()
	{
		User user = generateUser();
		String error = "";
		
		if(user.getUsername() == null)
			error += "Please enter a username\n";
		
		else if(!user.getUsername().equals(account.getUsername()))
			if(user.getUsername().length() < 5)
				error += "Username length needs to be at least 5 characters\n";
			else if(user.doesUsernameExist())
				error += "Username is taken\n";
		
		if(user.getEmail() == null && user.getNumber() == 0)
			error += "Please enter an email address or a phone number\n";
		else
		{
			if(user.getEmail() != null && !user.getEmail().equals(account.getEmail()))
				if(user.doesEmailExist())
					error += "Email Address is taken\n";
				else if(!user.getEmail().contains(".com"))
					error += "Please enter a valid email. A valid email contains the '.com' substring\n";
			
			if(user.getNumber() != 0 && user.getNumber() != account.getNumber())
				if(user.doesNumberExist())
					error += "Phone number is taken\n";
				else if(String.valueOf(user.getNumber()).length() < 10)
					error += "Please enter valid phone number. A valid number has 10 digits\n0000000000 is an invalid number\n";
		}
		return error;
	}
	
	public void configureFields()
	{
		Color fg = Color.BLACK;
		program.setAccount(account.getDetails());
		account = account.getDetails();
		
		view.getUsername().setText(account.getUsername());
		view.getUsername().setForeground(fg);
		if(account.getEmail() != null)
		{
			view.getEmail().setText(account.getEmail());
			view.getEmail().setForeground(fg);
		}
		if(account.getNumber() != 0)
		{
			view.getNumber().setText(String.valueOf(account.getNumber()));
			view.getNumber().setForeground(fg);
		}
		
		if(account.getGender() != null)
			if(account.getGender().equals(GENDER.M.toString()))
				view.getMale().setSelected(true);
			else
				view.getFemale().setSelected(true);
		
		
		
		if(account.getBirthday() != null)
		{	
			Calendar cal = Calendar.getInstance();
			cal.setTime(account.getBirthday());
			view.getYear().setSelectedItem(cal.get(Calendar.YEAR));
			view.getMonth().setSelectedItem(cal.get(Calendar.MONTH) + 1);
			view.getDate().setSelectedItem(cal.get(Calendar.DATE));
		}
		
		if(account.isForDeletion())
			view.getDelete().setText("Cancel Delete");
	}

	public Addresses getAddressview() {
		return addressview;
	}

	public BankAccounts getBankview() {
		return bankview;
	}

	public CreditCards getCardsview() {
		return cardsview;
	}

	public Orders getOrdersview() {
		return ordersview;
	}

	public void setAddressview(Addresses addressview) {
		this.addressview = addressview;
	}

	public void setBankview(BankAccounts bankview) {
		this.bankview = bankview;
	}

	public void setCardsview(CreditCards cardsview) {
		this.cardsview = cardsview;
	}

	public void setOrdersview(Orders ordersview) {
		this.ordersview = ordersview;
	}
	
	
}
