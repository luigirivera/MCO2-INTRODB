package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import controller.CorporateMainMenuController;

public class CorporateMainMenu extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton product, account, logout;
	private CorporateMainMenuController controller;
	
	public CorporateMainMenu()
	{
		super("Stop 'N Shop");
		
		setLayout(null);
		setSize(600,450);
		instantiate();
		initialize();
		addListeners();
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void instantiate()
	{
		product = new JButton("Products");
		account = new JButton("Accounts");
		logout = new JButton("Logout");
	}
	
	private void initialize()
	{
		add(product);
		add(account);
		add(logout);
		
		Font font = new Font("Arial", Font.BOLD, 25);
		product.setFont(font);
		account.setFont(font);
		logout.setFont(font);
		
		product.setSize(400, 50);
		product.setLocation(this.getWidth()/2 - product.getWidth()/2 + 10, this.getHeight()/6);
		
		account.setSize(product.getSize());
		account.setLocation(product.getX(), product.getY() + product.getHeight() + 50);
		
		logout.setSize(account.getSize());
		logout.setLocation(account.getX(), account.getY() + account.getHeight() + 50);
	}
	
	public void addController(CorporateMainMenuController controller)
	{ this.controller = controller;	}
	
	private void addListeners()
	{
		product.addActionListener(new productsListener());
		logout.addActionListener(new logoutListener());
	}
	
	class productsListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			controller.toggleProducts();
			
		}
		
	}
	
	class logoutListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			controller.logout();
		}
		
	}
	


}
