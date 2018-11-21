package view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ProductsView extends JFrame {
	protected static final long serialVersionUID = 1L;
	protected JTextField category, brand, lowPrice, highPrice;
	protected JComboBox<String> order;
	protected JButton apply;
	protected JPopupMenu rightClick;
	protected JTable productsTable;
	protected DefaultTableModel modelProductsTable;
	protected JScrollPane scrollProductsTable;
	
	public ProductsView()
	{
		super("Products");
		
		setLayout(null);
		setSize(1250,450);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	protected void commonInstantiate()
	{
		category = new JTextField(PLACEHOLDER.CATEGORY.toString());
		brand = new JTextField(PLACEHOLDER.BRAND.toString());
		
		lowPrice = new JTextField();
		highPrice = new JTextField();
		
		order = new JComboBox<String>();
		
		apply = new JButton("Apply");
		
		rightClick = new JPopupMenu();
		
		modelProductsTable = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		productsTable = new JTable(modelProductsTable);
		
		scrollProductsTable = new JScrollPane(productsTable);
	}
	
	protected void commonInitialize()
	{
		add(category);
		add(brand);
		add(lowPrice);
		add(highPrice);
		add(order);
		add(apply);
		
		add(scrollProductsTable);
		
		apply.setSize(100, 40);
		apply.setLocation(this.getWidth() - apply.getWidth() - 10, 10);
		
		scrollProductsTable.setSize(this.getWidth(), this.getHeight() - apply.getHeight());
		scrollProductsTable.setLocation(0, apply.getY() + apply.getHeight());
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

	public JTextField getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(JTextField lowPrice) {
		this.lowPrice = lowPrice;
	}

	public JTextField getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(JTextField highPrice) {
		this.highPrice = highPrice;
	}

	public JComboBox<String> getOrder() {
		return order;
	}

	public void setOrder(JComboBox<String> order) {
		this.order = order;
	}

	public JTable getProductsTable() {
		return productsTable;
	}

	public void setProductsTable(JTable productsTable) {
		this.productsTable = productsTable;
	}

	public DefaultTableModel getModelProductsTable() {
		return modelProductsTable;
	}

	public void setModelProductsTable(DefaultTableModel modelProductsTable) {
		this.modelProductsTable = modelProductsTable;
	}

	public JScrollPane getScrollProductsTable() {
		return scrollProductsTable;
	}

	public void setScrollProductsTable(JScrollPane scrollProductsTable) {
		this.scrollProductsTable = scrollProductsTable;
	}
	
	
}
