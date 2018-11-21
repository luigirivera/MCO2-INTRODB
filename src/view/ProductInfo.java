package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.ProductInfoController;

public class ProductInfo extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton save, cancel;
	private JTextField pName, category, brand, stock, price, discount, shipping;
	private JTextArea description;
	private ProductInfoController controller;
	private boolean modifyMode;
	
	public ProductInfo()
	{
		super("Product Info");
		setLayout(null);
		setSize(500,500);
		instantiate();
		initialize();
		addListeners();
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void instantiate()
	{
		save = new JButton(PLACEHOLDER.SAVE.toString());
		cancel = new JButton(PLACEHOLDER.CANCEL.toString());
		
		pName = new JTextField(PLACEHOLDER.NAME.toString());
		category = new JTextField(PLACEHOLDER.CATEGORY.toString());
		brand = new JTextField(PLACEHOLDER.BRAND.toString());
		stock = new JTextField(PLACEHOLDER.STOCK.toString());
		price = new JTextField(PLACEHOLDER.PRICE.toString());
		discount = new JTextField(PLACEHOLDER.DISCOUNT.toString());
		shipping = new JTextField(PLACEHOLDER.SHIPPING.toString());

		description = new JTextArea(PLACEHOLDER.DESCRIPTION.toString());
		
		modifyMode = false;
	}
	
	private void initialize()
	{
		add(save);
		add(cancel);
		
		add(pName);
		add(category);
		add(brand);
		add(description);
		add(stock);
		add(price);
		add(discount);
		add(shipping);
		
		Color fg = Color.GRAY;
		
		pName.setForeground(fg);
		category.setForeground(fg);
		brand.setForeground(fg);
		description.setForeground(fg);
		stock.setForeground(fg);
		price.setForeground(fg);
		discount.setForeground(fg);
		shipping.setForeground(fg);
		
		
		pName.setSize(this.getWidth() - 20, 30);
		pName.setLocation(10, 10);
		
		category.setSize(pName.getWidth()/2, pName.getHeight());
		category.setLocation(pName.getX(), pName.getY() + pName.getHeight() + 10);
		
		brand.setSize(category.getSize());
		brand.setLocation(category.getX() + category.getWidth(), category.getY());
		
		description.setSize(pName.getWidth(), 200);
		description.setLocation(category.getX(), category.getY() + category.getHeight() + 10);
		
		stock.setSize(brand.getSize());
		stock.setLocation(description.getX(), description.getY() + description.getHeight() + 10);
		
		price.setSize(stock.getSize());
		price.setLocation(stock.getX() + stock.getWidth(), stock.getY());
		
		discount.setSize(stock.getSize());
		discount.setLocation(stock.getX(), stock.getY() + stock.getHeight() + 10);
		
		shipping.setSize(discount.getSize());
		shipping.setLocation(discount.getX() + discount.getWidth(), discount.getY());
		
		save.setSize(this.getWidth()/2, 50);
		save.setLocation(0, this.getHeight() - save.getHeight() - 50);
		
		cancel.setSize(save.getSize());
		cancel.setLocation(save.getX() + save.getWidth(), save.getY());
	
	}
	
	public void addController(ProductInfoController controller) { this.controller = controller; }
	
	public void setData(String nameTxt, String catTxt, String brandTxt, String descTxt, String stockTxt, String priceTxt, String discountTxt, String shippingTxt)
	{
		Color fg = Color.BLACK;
		
		pName.setForeground(fg);
		category.setForeground(fg);
		brand.setForeground(fg);	
		stock.setForeground(fg);
		price.setForeground(fg);
		shipping.setForeground(fg);
		
		pName.setText(nameTxt);
		category.setText(catTxt);
		brand.setText(brandTxt);
		stock.setText(stockTxt);
		price.setText(priceTxt);
		shipping.setText(shippingTxt);
		
		if(descTxt != null)
		{
			description.setForeground(fg);
			description.setText(descTxt);
		}
		
		if(discountTxt != null)
		{
			discount.setForeground(fg);
			discount.setText(discountTxt);
		}
		
		modifyMode = true;
	}
	
	private void addListeners()
	{
		save.addActionListener(new buttonListener());
		cancel.addActionListener(new buttonListener());
		this.addWindowListener(new disposeListener());
		pName.addFocusListener(new nameListener());
		category.addFocusListener(new categoryListener());
		brand.addFocusListener(new brandListener());
		description.addFocusListener(new descriptionListener());
		stock.addFocusListener(new stockListener());
		price.addFocusListener(new priceListener());
		discount.addFocusListener(new discountListener());
		shipping.addFocusListener(new shippingListener());
	}
	
	class buttonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(arg0.getSource().equals(save))
				if (controller.areFieldsValid())
					if(!modifyMode && !controller.doesProductExist())
					{
						controller.addProduct();
						controller.dispose();
					}
					else if(modifyMode)
					{
						controller.updateProduct();
						controller.dispose();
					}
					else JOptionPane.showMessageDialog(null, controller.getExistError(), "Error", JOptionPane.ERROR_MESSAGE);
				else JOptionPane.showMessageDialog(null, controller.getFieldsError(), "Error", JOptionPane.ERROR_MESSAGE);
			else
				controller.dispose();
		}
		
	}
	
	class categoryListener extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(category.getText().equals(PLACEHOLDER.CATEGORY.toString()))
			{
				category.setText("");
				category.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(category.getText().isEmpty())
			{
				category.setText(PLACEHOLDER.CATEGORY.toString());
				category.setForeground(Color.GRAY);
			}			
		}
	}
	
	class shippingListener extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(shipping.getText().equals(PLACEHOLDER.SHIPPING.toString()))
			{
				shipping.setText("");
				shipping.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(shipping.getText().isEmpty())
			{
				shipping.setText(PLACEHOLDER.SHIPPING.toString());
				shipping.setForeground(Color.GRAY);
			}			
		}
	}
	
	class discountListener extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(discount.getText().equals(PLACEHOLDER.DISCOUNT.toString()))
			{
				discount.setText("");
				discount.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(discount.getText().isEmpty())
			{
				discount.setText(PLACEHOLDER.DISCOUNT.toString());
				discount.setForeground(Color.GRAY);
			}			
		}
	}
	
	class priceListener extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(price.getText().equals(PLACEHOLDER.PRICE.toString()))
			{
				price.setText("");
				price.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(price.getText().isEmpty())
			{
				price.setText(PLACEHOLDER.PRICE.toString());
				price.setForeground(Color.GRAY);
			}			
		}
	}
	
	class stockListener extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(stock.getText().equals(PLACEHOLDER.STOCK.toString()))
			{
				stock.setText("");
				stock.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(stock.getText().isEmpty())
			{
				stock.setText(PLACEHOLDER.STOCK.toString());
				stock.setForeground(Color.GRAY);
			}			
		}
	}
	
	class descriptionListener extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(description.getText().equals(PLACEHOLDER.DESCRIPTION.toString()))
			{
				description.setText("");
				description.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(description.getText().isEmpty())
			{
				description.setText(PLACEHOLDER.DESCRIPTION.toString());
				description.setForeground(Color.GRAY);
			}			
		}
	}
	
	class brandListener extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(brand.getText().equals(PLACEHOLDER.BRAND.toString()))
			{
				brand.setText("");
				brand.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(brand.getText().isEmpty())
			{
				brand.setText(PLACEHOLDER.BRAND.toString());
				brand.setForeground(Color.GRAY);
			}			
		}
	}
	
	class nameListener extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(pName.getText().equals(PLACEHOLDER.NAME.toString()))
			{
				pName.setText("");
				pName.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(pName.getText().isEmpty())
			{
				pName.setText(PLACEHOLDER.NAME.toString());
				pName.setForeground(Color.GRAY);
			}			
		}
	}
	
	class disposeListener extends WindowAdapter{
		@Override
		public void windowClosed(WindowEvent e) {
			controller.dispose();
		}
	}

	public JButton getSave() {
		return save;
	}

	public void setSave(JButton save) {
		this.save = save;
	}

	public JButton getCancel() {
		return cancel;
	}

	public void setCancel(JButton cancel) {
		this.cancel = cancel;
	}

	public JTextField getPName() {
		return pName;
	}

	public void setPName(JTextField pName) {
		this.pName = pName;
	}

	public JTextField getCategory() {
		return category;
	}

	public void setCategory(JTextField category) {
		this.category = category;
	}

	public JTextField getBrand() {
		return brand;
	}

	public void setBrand(JTextField brand) {
		this.brand = brand;
	}

	public JTextField getStock() {
		return stock;
	}

	public void setStock(JTextField stock) {
		this.stock = stock;
	}

	public JTextField getPrice() {
		return price;
	}

	public void setPrice(JTextField price) {
		this.price = price;
	}

	public JTextField getDiscount() {
		return discount;
	}

	public void setDiscount(JTextField discount) {
		this.discount = discount;
	}

	public JTextField getShipping() {
		return shipping;
	}

	public void setShipping(JTextField shipping) {
		this.shipping = shipping;
	}

	public JTextArea getDescription() {
		return description;
	}

	public void setDescription(JTextArea description) {
		this.description = description;
	}

	public boolean isModifyMode() {
		return modifyMode;
	}

	public void setModifyMode(boolean modifyMode) {
		this.modifyMode = modifyMode;
	}
	
	
	
}
