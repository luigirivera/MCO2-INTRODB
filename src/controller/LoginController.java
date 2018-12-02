package controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import driver.StopNShop;
import model.User;
import view.Login;
import view.PLACEHOLDER;

public class LoginController {
	private Login view;
	private User account;
	private StopNShop program;
	
	public LoginController(Login view, StopNShop program)
	{
		this.view = view;
		this.program = program;
		view.addController(this);
	}

	public void loginAccount()
	{
		account.setLogin();
		User user = account.getDetails();
		if(user.checkCorporate())
			user.setCorporate(true);
		else
			user.setCorporate(false);
		program.setAccount(user);
		account = account.getDetails();
		program.login();
	}
	
	public void clearTries()
	{
		account.setTries(0);
	}
	
	public void updateAccountLock()
	{
		account.updateAccountLock();
	}
	
	public boolean verifyPassword()
	{
		return account.getPassword().equals(hash(String.valueOf(view.getPassword().getPassword())));
	}
	public boolean isLocked()
	{
		return account.isLocked();
	}
	
	public void lockAccount()
	{
		account.setLocked(true);
	}
	
	public int getTries()
	{
		return account.getTries();
	}
	
	public void addTries()
	{
		account.setTries(account.getTries() + 1);
	}
	
	public void getAccountLogin() {
		account = new User();
		account.setUsername(view.getUsername().getText());
		
		account = account.getAccountLogin();
	}
	
	public boolean isLoginValid()
	{
		return checkUsername().concat(checkPassword()).isEmpty();
	}
	
	public String checkUsername()
	{
		if(view.getUsername().getText().isEmpty() ||
				view.getUsername().getText().equals(PLACEHOLDER.USERNAME.toString()))
			return "Please enter a username\n";
		
		return "";
	}
	
	public String checkPassword()
	{
		if(String.valueOf(view.getPassword().getPassword()).isEmpty() ||
				String.valueOf(view.getPassword().getPassword()).equals(PLACEHOLDER.PASSWORD.toString()))
			return "Please enter a password\n";
		
		return "";
	}
	
	public boolean doesAccountExist() {
		User user = new User();

		user.setUsername(view.getUsername().getText());
		
		return user.doesUsernameExist();
	}
	
	public void registerAccount() {
		User user = new User();
		
		user.setUsername(view.getSignupPanel().getUsername().getText());
		user.setPassword(hash(String.valueOf(view.getSignupPanel().getPassword().getPassword())));
		
		if(view.getSignupPanel().getStatus().equals(PLACEHOLDER.EMAIL))
			user.setEmail(view.getSignupPanel().getContact().getText());
		else
			user.setNumber(Long.parseLong(view.getSignupPanel().getContact().getText()));
		
		user.registerAccount();
		user.setId(user.getuserID());
		user.setConsumer();
		user.followAccount(1);
	}
	
	public boolean isAccountFree()
	{
		return doesUsernameExist().concat(doesContactExist()).isEmpty();
	}
	
	public String doesUsernameExist() {
		User user = new User();
		user.setUsername(view.getSignupPanel().getUsername().getText());
		if(user.doesUsernameExist())
			return "Username is taken\n";
		
		return "";
	}
	
	public String doesContactExist() {
		User user = new User();
		
		if(view.getSignupPanel().getStatus().equals(PLACEHOLDER.EMAIL))
		{
			user.setEmail(view.getSignupPanel().getContact().getText());
			
			if(user.doesEmailExist())
				return "Email Address is taken\n";
				
		}
		else
		{
			user.setNumber(Long.parseLong(view.getSignupPanel().getContact().getText()));
			
			if(user.doesNumberExist())
				return "Phone Number is taken\n";
		}
			
		
		
		return "";
	}
	
	public void toggleLogin(boolean enable) {
		view.getUsername().setEnabled(enable);
		view.getPassword().setEnabled(enable);
		view.getLogin().setEnabled(enable);
		view.getSignup().setEnabled(enable);
		
		if(!enable)
			view.setSize(400, 580);
		else
			view.setSize(400,250);
	}
	
	public boolean isSignupValid()
	{
		return getErrors().isEmpty();
	}
	
	public String getErrors()
	{
		String error = "";
		
		if(view.getSignupPanel().getUsername().getText().equals(PLACEHOLDER.USERNAME.toString()) ||
				view.getSignupPanel().getUsername().getText().trim().isEmpty())
			
			error += "Please enter a username\n";
		else if (view.getSignupPanel().getUsername().getText().length() < 5)
			error += "Username length needs to be at least 5 characters\n";
		
		if(view.getSignupPanel().getStatus().equals(PLACEHOLDER.EMAIL))
			if((view.getSignupPanel().getContact().getText().equals(PLACEHOLDER.EMAIL.toString()) ||
					view.getSignupPanel().getContact().getText().trim().isEmpty()))
					
					error += "Please enter an email\n";
			else if(!view.getSignupPanel().getContact().getText().contains(".com"))
					error += "Please enter a valid email. A valid email contains the '.com' substring\n";
			
		else if(view.getSignupPanel().getStatus().equals(PLACEHOLDER.NUMBER))
					
					try {
						if(view.getSignupPanel().getContact().getText().trim().isEmpty() || 
							view.getSignupPanel().getContact().getText().equals(PLACEHOLDER.NUMBER.toString()))
							error += "Please enter a phone number\n";
						else if(Long.parseLong(view.getSignupPanel().getContact().getText()) == 0);
						else if(String.valueOf(view.getSignupPanel().getContact().getText()).length() < 10)
							throw new NumberFormatException();
					}catch(Exception e) {
						error += "Please enter valid phone number. A valid number has 10 digits\n0000000000 is an invalid number";
					}
		
		if(String.valueOf(view.getSignupPanel().getPassword().getPassword()).equals(PLACEHOLDER.PASSWORD.toString()) ||
			String.valueOf(view.getSignupPanel().getPassword().getPassword()).trim().isEmpty())
			
			error += "Please enter a password\n";
			
		if(String.valueOf(view.getSignupPanel().getConfirmpass().getPassword()).equals(PLACEHOLDER.PASSWORD.toString()) ||
			String.valueOf(view.getSignupPanel().getConfirmpass().getPassword()).trim().isEmpty())
			
			error += "Please enter the confirmation password\n";
		
		if((!String.valueOf(view.getSignupPanel().getPassword().getPassword()).trim().isEmpty() &&
			!String.valueOf(view.getSignupPanel().getPassword().getPassword()).equals(PLACEHOLDER.PASSWORD.toString()))
			&&
			(!String.valueOf(view.getSignupPanel().getConfirmpass().getPassword()).trim().isEmpty() &&
			!String.valueOf(view.getSignupPanel().getConfirmpass().getPassword()).equals(PLACEHOLDER.PASSWORD.toString())))
			
			if(!String.valueOf(view.getSignupPanel().getPassword().getPassword()).equals(String.valueOf(view.getSignupPanel().getConfirmpass().getPassword())))
				error += "Passwords do not match\n";
		
			if(String.valueOf(view.getSignupPanel().getPassword().getPassword()).length() < 6)
				error += "Password length needs to be greater than 6 characters";
				

		
		return error;
	}
	
    private String hash(String pass)
    {
        MessageDigest md;
        byte[] sha1hash = new byte[40];;
        try {
            md = MessageDigest.getInstance("SHA-1");
            md.update(pass.getBytes("iso-8859-1"), 0, pass.length());
            sha1hash = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return convertToHex(sha1hash);
    }

    private String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        }
        return buf.toString();
    }
}
