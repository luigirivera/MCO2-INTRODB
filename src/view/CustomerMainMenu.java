package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import controller.CustomerMainMenuController;

public class CustomerMainMenu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton seller, products, cart, settings, logout;
	private CustomerMainMenuController controller;
	
	public CustomerMainMenu()
	{
		super("Shopee");
		
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
		seller = new JButton("Seller Portal");
		products = new JButton("Products");
		cart = new JButton("Cart");
		settings = new JButton("Settings");
		logout = new JButton("Logout");
	}
	
	private void initialize()
	{
		add(seller);
		add(products);
		add(cart);
		add(settings);
		add(logout);
		
		
		Font font = new Font("Arial", Font.BOLD, 25);
		products.setFont(font);
		cart.setFont(font);
		settings.setFont(font);
		logout.setFont(font);
		
		
		seller.setSize(150, 30);
		seller.setLocation(this.getWidth() - seller.getWidth(), 0);
		
		products.setSize(400, 50);
		products.setLocation(this.getWidth()/2 - products.getWidth()/2 + 10, seller.getY() + seller.getHeight() + 30);
	
		cart.setSize(products.getSize());
		cart.setLocation(products.getX(), products.getY() + products.getHeight() + 30);
		
		settings.setSize(cart.getSize());
		settings.setLocation(cart.getX(), cart.getY() + cart.getHeight() + 30);
		
		logout.setSize(settings.getSize());
		logout.setLocation(settings.getX(), settings.getY() + settings.getHeight() + 30);
	}
	
	private void addListeners()
	{
		settings.addActionListener(new settingsListener());
		logout.addActionListener(new logoutListener());
		seller.addActionListener(new sellerListener());
		products.addActionListener(new productsListener());
		cart.addActionListener(new cartListener());
	}
	
	public void addController(CustomerMainMenuController controller)
	{
		this.controller = controller;
	}
	
	class cartListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.toggleCart();
			
		}
		
	}
	
	class productsListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.toggleProducts();
			
		}
		
	}
	
	class sellerListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.toggleSellerPortal();
			
		}
		
	}
	
	class logoutListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			controller.logout();
		}
		
	}
	
	class settingsListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			controller.toggleSettings();
			
		}
		
	}
}
