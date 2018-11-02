package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JTextField username;
	private JPasswordField password;
	private JButton login, signup;
	private JLabel welcome;
	
	public Login() {
		super("Stop 'N Shop");
		
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
		username = new JTextField();
		password = new JPasswordField();
		
		login = new JButton("Login");
		signup = new JButton("Signup");
		
		welcome = new JLabel("Welcome to Stop 'N Shop");
	}
	
	private void initialize()
	{
		add(welcome);
		add(username);
		add(password);
		add(login);
		add(signup);
		
		welcome.setFont(new Font("Arial", Font.BOLD, 25));
		username.setText("username");
		username.setForeground(Color.GRAY);
		password.setText("password");
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
	}
	
	
	private void setListeners() {
		username.addFocusListener(new usernameFocus());
		password.addFocusListener(new passwordFocus());
	}
	
	class usernameFocus extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(username.getText().equals("username"))
			{
				username.setText("");
				username.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(username.getText().isEmpty())
			{
				username.setText("username");
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
}
