package view;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import controller.SellerPortalController;

public class SellerPortal extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JButton products, sales;
	private SellerPortalController controller;
	
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
	}
	
	private void initialize()
	{
		add(products);
		add(sales);
		
		Font font = new Font("Arial", Font.BOLD, 25);
		products.setFont(font);
		sales.setFont(font);
		
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
//		products.addActionListener(new productsListener());
//		sales.addActionListener(new salesListener());
		this.addWindowListener(new disposeListener());
	}
	
	class disposeListener extends WindowAdapter{
		@Override
		public void windowClosed(WindowEvent e) {
			controller.close();
		}
	}
}
