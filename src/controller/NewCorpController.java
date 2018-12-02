package controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import model.User;
import view.NewCorporateAccount;
import view.PLACEHOLDER;

public class NewCorpController {

	private NewCorporateAccount view;
	private AccountsController accounts;
	
	public NewCorpController(NewCorporateAccount view, AccountsController accounts) {
		this.view = view;
		this.accounts = accounts;
		
		view.addController(this);
	}

	public void dispose()
	{
		view.dispose();
		accounts.setNewCorp(null);
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
		user.setCorporate();
		
		accounts.update();
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
	
	public boolean isSignupValid()
	{
		return getErrors().isEmpty();
	}
	
	public String getErrors()
	{
		String error = "";
		System.out.println(view.getSignupPanel().getStatus().equals(PLACEHOLDER.EMAIL));
		
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
