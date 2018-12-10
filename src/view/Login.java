package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.LoginController;

public class Login extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JTextField username;
	private JPasswordField password;
	private JButton login, signup;
	private JLabel welcome;
	private Signup signupPanel;
	private LoginController controller;
	
	public Login() {
		super("Shopee");
		
		instantiate();
		initialize();
		setListeners();
		
		setSize(400,250);
		setLayout(null);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void instantiate()
	{
		username = new JTextField(PLACEHOLDER.USERNAME.toString());
		password = new JPasswordField(PLACEHOLDER.PASSWORD.toString());
		
		login = new JButton("Login");
		signup = new JButton("Signup");
		
		signupPanel = new Signup();
		
		welcome = new JLabel("Welcome to Shopee");
	}
	
	private void initialize()
	{
		add(welcome);
		add(username);
		add(password);
		add(login);
		add(signup);
		add(signupPanel);
		
		welcome.setFont(new Font("Arial", Font.BOLD, 25));
		username.setForeground(Color.GRAY);
		password.setForeground(Color.GRAY);
		
		welcome.setSize(320, 50);
		welcome.setLocation(this.getX()/2 - welcome.getX()/2 + 10, 20);
		
		username.setSize(welcome.getWidth(), 30);
		username.setLocation(welcome.getX(), welcome.getY() + welcome.getHeight() + 5);
		
		password.setSize(username.getWidth(), 30);
		password.setLocation(username.getX(), username.getY() + username.getHeight() + 1);
		
		login.setSize(password.getWidth()/2, 50);
		login.setLocation(password.getX(), password.getY() + password.getHeight() + 5);
		
		signup.setSize(login.getWidth(), login.getHeight());
		signup.setLocation(login.getX() + login.getWidth(), login.getY());
		
		signupPanel.setSize(400, 300);
		signupPanel.setLocation(0, 230);
	}
	
	public void addController(LoginController controller) {
		this.controller = controller;
	}
	
	private void setListeners() {
		username.addFocusListener(new usernameFocus());
		password.addFocusListener(new passwordFocus());
		signup.addActionListener(new signupListener());
		signupPanel.getSignup().addActionListener(new signupConfirmListener());
		signupPanel.getCancel().addActionListener(new cancelListener());
		login.addActionListener(new loginListener());
	}
	
	class loginListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(controller.isLoginValid())
				if(controller.doesAccountExist())
				{
					controller.getAccountLogin();
					if(!controller.isLocked())
						if(controller.verifyPassword())
						{
							controller.loginAccount();
							controller.clearTries();
							dispose();
						}
							
						else
						{
							controller.addTries();
							if(controller.getTries() == 3)
							{
								JOptionPane.showMessageDialog(null, "Account Locked\nPlease contact an administrator to unlock your account", "Error", JOptionPane.ERROR_MESSAGE);
								controller.lockAccount();
							}
								
							else
								JOptionPane.showMessageDialog(null, "Password incorrect\nYou now have " + controller.getTries() + " out of 3\nbefore your account gets locked.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					else
						JOptionPane.showMessageDialog(null, "Account Locked\nPlease contact an administrator to unlock your account", "Error", JOptionPane.ERROR_MESSAGE);
					
					controller.updateAccountLock();
				}
				else
					JOptionPane.showMessageDialog(null, "Account doesn't exist", "Error", JOptionPane.ERROR_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, controller.checkUsername().concat(controller.checkPassword()), "Error", JOptionPane.ERROR_MESSAGE);
			
			
		}
		
	}
	
	class usernameFocus extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(username.getText().equals(PLACEHOLDER.USERNAME.toString()))
			{
				username.setText("");
				username.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(username.getText().isEmpty())
			{
				username.setText(PLACEHOLDER.USERNAME.toString());
				username.setForeground(Color.GRAY);
			}			
		}
	}
	
	class passwordFocus extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(String.valueOf(((JPasswordField)e.getSource()).getPassword()).equals(PLACEHOLDER.PASSWORD.toString()))
			{
				((JPasswordField)e.getSource()).setText("");
				((JPasswordField)e.getSource()).setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(String.valueOf(((JPasswordField)e.getSource()).getPassword()).isEmpty())
			{
				((JPasswordField)e.getSource()).setText(PLACEHOLDER.PASSWORD.toString());
				((JPasswordField)e.getSource()).setForeground(Color.GRAY);
			}			
		}
	}
	
	class signupListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			controller.toggleLogin(false);
		}
		
	}
	
	class cancelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			controller.toggleLogin(true);
			signupPanel.clearSignup();
		}
		
	}
	
	class signupConfirmListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (controller.isSignupValid())
				if(controller.isAccountFree())
				{
					controller.registerAccount();
					signupPanel.clearSignup();
					controller.toggleLogin(true);
				}
				else

					JOptionPane.showMessageDialog(null, controller.doesUsernameExist().concat(controller.doesContactExist()), "Error", JOptionPane.ERROR_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, controller.getErrors(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}

//GETTERS AND SETTERS//
	public JTextField getUsername() {
		return username;
	}

	public JPasswordField getPassword() {
		return password;
	}

	public JButton getLogin() {
		return login;
	}

	public JButton getSignup() {
		return signup;
	}

	public JLabel getWelcome() {
		return welcome;
	}

	public Signup getSignupPanel() {
		return signupPanel;
	}
	
}
