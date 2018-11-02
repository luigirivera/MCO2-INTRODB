package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Signup extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextField username, contact;
	private JPasswordField password, confirmpass;
	private JButton signup, cancel, swap;
	private String status;
	
	private final String email = "Signup using Email";
	private final String number = "Signup using Number";
	
	public Signup() {
		setLayout(null);
		setSize(400, 300);
		instantiate();
		initialize();
		addListeners();
	}
	
	private void instantiate() {
		username = new JTextField();
		contact = new JTextField();
		
		password = new JPasswordField();
		confirmpass = new JPasswordField();
		
		signup = new JButton("Signup");
		cancel = new JButton("Cancel");
		swap = new JButton(email);
		
		status = "NUMBER";
	}
	
	private void initialize() {
		add(username);
		add(contact);
		add(password);
		add(confirmpass);
		add(signup);
		add(cancel);
		add(swap);
		
		username.setText("Username");
		username.setForeground(Color.GRAY);
		contact.setText("Phone Number");
		contact.setForeground(Color.GRAY);
		password.setText("password");
		password.setForeground(Color.GRAY);
		confirmpass.setText("password");
		confirmpass.setForeground(Color.GRAY);
		
		username.setSize(320,30);
		username.setLocation(10, 20);
		
		contact.setSize(username.getSize());
		contact.setLocation(username.getX(), username.getY() + username.getHeight() + 2);
		
		password.setSize(contact.getSize());
		password.setLocation(contact.getX(), contact.getY() + contact.getHeight() + 30);
		
		confirmpass.setSize(password.getSize());
		confirmpass.setLocation(password.getX(), password.getY() + password.getHeight() + 2);
		
		
		signup.setSize(this.getWidth()/3 - 30, 50);
		signup.setLocation(this.getWidth()-signup.getWidth(), this.getHeight() - signup.getHeight());
	
		cancel.setSize(signup.getSize());
		cancel.setLocation(signup.getX() - cancel.getWidth(), signup.getY());
		
		swap.setSize(cancel.getWidth() + 60, cancel.getHeight());
		swap.setLocation(username.getX(), cancel.getY());
	}
	
	private void addListeners() {
		swap.addActionListener(new swapListener());
		contact.addFocusListener(new contactFocus());
		username.addFocusListener(new usernameFocus());
		password.addFocusListener(new passwordFocus());
		confirmpass.addFocusListener(new confirmFocus());
	}
	
	class swapListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(swap.getText().equals(email))
			{
				swap.setText(number);
				status = "EMAIL";
				contact.setText("Email Address");
			}
				
			else
			{
				swap.setText(email);
				status = "NUMBER";
				contact.setText("Phone Number");
			}
			
			contact.setForeground(Color.GRAY);
				
		}
		
	}
	
	class contactFocus extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(contact.getText().equals("Email Address") || contact.getText().equals("Phone Number"))
			{
				contact.setText("");
				contact.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(contact.getText().isEmpty())
			{
				if(status.equals("EMAIL"))
					contact.setText("Email Address");
				else
					contact.setText("Phone Number");
				contact.setForeground(Color.GRAY);
			}			
		}
	}
	
	class usernameFocus extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(username.getText().equals("Username"))
			{
				username.setText("");
				username.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(username.getText().isEmpty())
			{
				username.setText("Username");
				username.setForeground(Color.GRAY);
			}			
		}
	}
	
	class passwordFocus extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(String.valueOf(password.getPassword()).equals("password"))
			{
				password.setText("");
				password.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(String.valueOf(password.getPassword()).isEmpty())
			{
				password.setText("password");
				password.setForeground(Color.GRAY);
			}			
		}
	}
	
	class confirmFocus extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(String.valueOf(confirmpass.getPassword()).equals("password"))
			{
				confirmpass.setText("");
				confirmpass.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(String.valueOf(confirmpass.getPassword()).isEmpty())
			{
				confirmpass.setText("password");
				confirmpass.setForeground(Color.GRAY);
			}			
		}
	}
	
//EXTRA METHODS//
	public void clearSignup()
	{
		username.setText("Username");
		username.setForeground(Color.GRAY);
		contact.setText("Phone Number");
		contact.setForeground(Color.GRAY);
		password.setText("password");
		password.setForeground(Color.GRAY);
		confirmpass.setText("password");
		confirmpass.setForeground(Color.GRAY);
		status = "NUMBER";
	}
	
//GETTERS AND SETTERS//
	public JTextField getUsername() {
		return username;
	}

	public JTextField getContact() {
		return contact;
	}

	public JPasswordField getPassword() {
		return password;
	}

	public JPasswordField getConfirmpass() {
		return confirmpass;
	}

	public JButton getSignup() {
		return signup;
	}

	public JButton getCancel() {
		return cancel;
	}

	public JButton getSwap() {
		return swap;
	}

	public String getStatus() {
		return status;
	}
}
