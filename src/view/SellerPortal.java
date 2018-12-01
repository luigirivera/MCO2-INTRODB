package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import controller.SellerPortalController;

public class SellerPortal extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JButton products, sales;
	private SellerPortalController controller;
	private JLabel followers, following;
	
	public SellerPortal()
	{
		super("Seller Portal");
		setLayout(null);
		setSize(500,400);
		instantiate();
		initialize();
		addListeners();
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void instantiate()
	{
		products = new JButton("Products");
		sales = new JButton("Sales");
		
		followers = new JLabel();
		following = new JLabel();
	}
	
	private void initialize()
	{
		add(products);
		add(sales);
		add(followers);
		add(following);
		
		Font font = new Font("Arial", Font.BOLD, 25);
		products.setFont(font);
		sales.setFont(font);
		followers.setFont(font);
		following.setFont(font);
		
		followers.setSize(250, 40);
		followers.setLocation(10, 0);
		
		following.setSize(followers.getSize());
		following.setLocation(10, followers.getY() + followers.getHeight());
		
		products.setSize(400, 50);
		products.setLocation(this.getWidth()/2 - products.getWidth()/2 + 10, this.getHeight()/4 - products.getHeight()/2 + 10);
		
		sales.setSize(products.getSize());
		sales.setLocation(this.getWidth()/2 - sales.getWidth()/2 + 10, this.getHeight()/2 + sales.getHeight()/2 + 10);
	}
	
	public void addController(SellerPortalController controller)
	{
		this.controller = controller;
	}
	
	private void addListeners()
	{
		products.addActionListener(new productsListener());
		sales.addActionListener(new salesListener());
		this.addWindowListener(new disposeListener());
	}
	
	class salesListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			controller.toggleSales();
		}
		
	}
	
	class disposeListener extends WindowAdapter{
		@Override
		public void windowClosed(WindowEvent e) {
			controller.close();
		}
	}
	
	class productsListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			controller.toggleProducts();
			
		}
		
	}

	public JLabel getFollowers() {
		return followers;
	}

	public void setFollowers(JLabel followers) {
		this.followers = followers;
	}

	public JLabel getFollowing() {
		return following;
	}

	public void setFollowing(JLabel following) {
		this.following = following;
	}
	

}
