package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.YearMonth;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import controller.SettingsController;
import model.GENDER;

public class Settings extends JFrame {
	private static final long serialVersionUID = 1L;

	private JButton save, passwordsave, address,bank,card,order,delete;
	private JPasswordField oldpass, newpass, confirmpass;
	private JTextField username, email, number;
	private JComboBox<Integer> month, date, year;
	private ButtonGroup genderGroup;
	private JRadioButton male, female;
	private SettingsController controller;
	
	public Settings()
	{
		super("Settings");
		
		setLayout(null);
		setSize(600,450);
		instantiate();
		initialize();
		addListeners();
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void instantiate()
	{
		save = new JButton("Update");
		passwordsave = new JButton("Update Password");
		address = new JButton("Addresses");
		bank = new JButton("Bank Accounts");
		card = new JButton("Credit Cards");
		order = new JButton("Orders");
		delete = new JButton("Delete Account");
		
		oldpass = new JPasswordField(PLACEHOLDER.PASSWORD.toString());
		newpass = new JPasswordField(PLACEHOLDER.PASSWORD.toString());
		confirmpass = new JPasswordField(PLACEHOLDER.PASSWORD.toString());
		
		username = new JTextField(PLACEHOLDER.USERNAME.toString());
		email = new JTextField(PLACEHOLDER.EMAIL.toString());
		number = new JTextField(PLACEHOLDER.NUMBER.toString());
		
		month = new JComboBox<Integer>();
		date = new JComboBox<Integer>();
		year = new JComboBox<Integer>();
		
		genderGroup = new ButtonGroup();
		male = new JRadioButton(GENDER.M.toString());
		female = new JRadioButton(GENDER.F.toString());
	}
	
	private void initialize()
	{
		add(address);
		add(bank);
		add(card);
		add(order);
		add(delete);
		add(username);
		add(email);
		add(number);
		add(oldpass);
		add(newpass);
		add(male);
		add(female);
		add(month);
		add(date);
		add(year);
		add(save);
		add(confirmpass);
		add(passwordsave);
		
		
		genderGroup.add(male);
		genderGroup.add(female);
		
		Font font = new Font("Arial", Font.PLAIN, 15);
		address.setFont(font);
		bank.setFont(font);
		card.setFont(font);
		order.setFont(font);
		delete.setFont(font);
		
		Color fg = Color.GRAY;
		
		username.setForeground(fg);
		email.setForeground(fg);
		number.setForeground(fg);
		oldpass.setForeground(fg);
		newpass.setForeground(fg);
		confirmpass.setForeground(fg);

		
		month.addItem(null);
		date.addItem(null);
		year.addItem(null);
		for(int i = 1; i<=12; i++) month.addItem(i);
		
		for(int i = 1; i<=31; i++) date.addItem(i);
		
		for(int i = Calendar.getInstance().get(Calendar.YEAR) - 150; i <= Calendar.getInstance().get(Calendar.YEAR); i++)
			year.addItem(i);
		
		address.setSize(150, 50);
		address.setLocation(10, 20);
		
		bank.setSize(address.getSize());
		bank.setLocation(address.getX(), address.getY() + address.getHeight() + 20);
		
		card.setSize(bank.getSize());
		card.setLocation(bank.getX(), bank.getY() + bank.getHeight() + 20);
		
		order.setSize(card.getSize());
		order.setLocation(card.getX(), card.getY() + card.getHeight() + 20);
		
		delete.setSize(order.getSize());
		delete.setLocation(order.getX(), order.getY() + order.getHeight() + 20);
		
		username.setSize(this.getWidth() - (address.getX() + address.getWidth() + 30) - 20, 30);
		username.setLocation(address.getX() + address.getWidth() + 30, address.getY());
		
		email.setSize(username.getSize());
		email.setLocation(username.getX(), username.getY() + username.getHeight() + 10);
		
		number.setSize(email.getSize());
		number.setLocation(email.getX(), email.getY() + email.getHeight() + 10);
		
		male.setSize(number.getWidth()/4, number.getHeight());
		male.setLocation(number.getX(), number.getY() + number.getHeight() + 10);
		
		female.setSize(male.getSize());
		female.setLocation(male.getX() + male.getWidth(), male.getY());
		
		month.setSize(number.getWidth()/3, number.getHeight());
		month.setLocation(male.getX(), female.getY() + female.getHeight() + 10);
		
		date.setSize(month.getSize());
		date.setLocation(month.getX() + month.getWidth(), month.getY());
		
		year.setSize(date.getSize());
		year.setLocation(date.getX() + date.getWidth(), date.getY());
		
		save.setSize(number.getWidth(), number.getHeight() + 10);
		save.setLocation(month.getX(), month.getY() + month.getHeight() + 10);
		
		oldpass.setSize(number.getSize());
		oldpass.setLocation(save.getX(), save.getY() + save.getHeight() + 30);
		
		newpass.setSize(oldpass.getWidth()/2, oldpass.getHeight());
		newpass.setLocation(oldpass.getX(), oldpass.getY() + oldpass.getHeight() + 10);
		
		confirmpass.setSize(newpass.getWidth(), newpass.getHeight());
		confirmpass.setLocation(newpass.getX() + newpass.getWidth(), newpass.getY());
		
		passwordsave.setSize(save.getSize());
		passwordsave.setLocation(newpass.getX(), newpass.getY() + newpass.getHeight() + 10);
	}
	
	public void addController(SettingsController controller)
	{
		this.controller = controller;
	}
	
	public void configureDates()
	{
		date.removeAllItems();
		date.addItem(null);
		
		if((Integer)year.getSelectedItem() != null && (Integer)month.getSelectedItem() != null)
		{
			int days = YearMonth.of((Integer)year.getSelectedItem(), (Integer)month.getSelectedItem()).lengthOfMonth();
			for(int i = 1; i<=days; i++)
				date.addItem(i);
		}
	}
	
	private void addListeners()
	{
		month.addActionListener(new dateListener());
		year.addActionListener(new dateListener());
		oldpass.addFocusListener(new passwordFocus());
		newpass.addFocusListener(new passwordFocus());
		confirmpass.addFocusListener(new passwordFocus());
		username.addFocusListener(new usernameFocus());
		email.addFocusListener(new emailFocus());
		number.addFocusListener(new numberFocus());
	}
	
	class dateListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			configureDates();
		}
		
	}
	
	class numberFocus extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(number.getText().equals(PLACEHOLDER.NUMBER.toString()))
			{
				number.setText("");
				number.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(number.getText().isEmpty())
			{
				number.setText(PLACEHOLDER.NUMBER.toString());
				number.setForeground(Color.GRAY);
			}			
		}
	}
	
	class emailFocus extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(email.getText().equals(PLACEHOLDER.EMAIL.toString()))
			{
				email.setText("");
				email.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(email.getText().isEmpty())
			{
				email.setText(PLACEHOLDER.EMAIL.toString());
				email.setForeground(Color.GRAY);
			}			
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

	public JButton getSave() {
		return save;
	}

	public JButton getPasswordsave() {
		return passwordsave;
	}

	public JButton getAddress() {
		return address;
	}

	public JButton getBank() {
		return bank;
	}

	public JButton getCard() {
		return card;
	}

	public JButton getOrder() {
		return order;
	}

	public JButton getDelete() {
		return delete;
	}

	public JPasswordField getOldpass() {
		return oldpass;
	}

	public JPasswordField getNewpass() {
		return newpass;
	}

	public JPasswordField getConfirmpass() {
		return confirmpass;
	}

	public JTextField getUsername() {
		return username;
	}

	public JTextField getEmail() {
		return email;
	}

	public JTextField getNumber() {
		return number;
	}

	public JComboBox<Integer> getMonth() {
		return month;
	}

	public JComboBox<Integer> getDate() {
		return date;
	}

	public JComboBox<Integer> getYear() {
		return year;
	}

	public ButtonGroup getGenderGroup() {
		return genderGroup;
	}

	public JRadioButton getMale() {
		return male;
	}

	public JRadioButton getFemale() {
		return female;
	}

	public SettingsController getController() {
		return controller;
	}
	
	
}
