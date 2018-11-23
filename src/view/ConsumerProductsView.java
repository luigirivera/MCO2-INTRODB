package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

import controller.ConsumerProductsController;

public class ConsumerProductsView extends ProductsView {
	private static final long serialVersionUID = 1L;
	private JMenuItem rate, cart, fave, follow;
	private ConsumerProductsController controller;
	
	public ConsumerProductsView()
	{
		super();
		commonInstantiate();
		instantiate();
		
		commonInitialize();
		initialize();
		generateTable();
		addListeners();
	}
	
	private void instantiate()
	{
		rate = new JMenuItem(PLACEHOLDER.RATE.toString());
		cart = new JMenuItem(PLACEHOLDER.CART.toString());
		fave = new JMenuItem(PLACEHOLDER.FAVE.toString());
		follow = new JMenuItem(PLACEHOLDER.FOLLOW.toString());
	}
	
	private void initialize()
	{
		rightClick.add(rate);
		rightClick.add(cart);
		rightClick.add(fave);
		rightClick.add(follow);
	}
	
	private void generateTable()
	{
		modelProductsTable.addColumn(PLACEHOLDER.NAME.toString());
		modelProductsTable.addColumn(PLACEHOLDER.CATEGORY.toString());
		modelProductsTable.addColumn(PLACEHOLDER.BRAND.toString());
		modelProductsTable.addColumn(PLACEHOLDER.SELLER.toString());
		modelProductsTable.addColumn(PLACEHOLDER.DESCRIPTION.toString());
		modelProductsTable.addColumn(PLACEHOLDER.FAVORITES.toString());
		modelProductsTable.addColumn(PLACEHOLDER.STOCK.toString());
		modelProductsTable.addColumn(PLACEHOLDER.PRICE.toString());
		modelProductsTable.addColumn(PLACEHOLDER.DISCOUNT.toString());
		modelProductsTable.addColumn(PLACEHOLDER.SHIPPING.toString());
		
		productsTable.getTableHeader().setResizingAllowed(false);
		productsTable.getTableHeader().setReorderingAllowed(false);
		productsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		productsTable.setOpaque(false);
		((DefaultTableCellRenderer)productsTable.getDefaultRenderer(Object.class)).setOpaque(false);
		
		scrollProductsTable.setOpaque(false);
		scrollProductsTable.getViewport().setOpaque(false);
	}
	
	public void addController(ConsumerProductsController controller) {this.controller = controller;}
	
	private void addListeners()
	{
		addWindowListener(new disposeListener());
		productsTable.addMouseListener(new rightClickListener());
		category.addFocusListener(new categoryFocus());
		brand.addFocusListener(new brandFocus());
		lowPrice.addFocusListener(new lowPriceFocus());
		highPrice.addFocusListener(new highPriceFocus());
		apply.addActionListener(new applyListener());
		fave.addActionListener(new FaveListener());
	}
	
	class FaveListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(fave.getText().equals(PLACEHOLDER.FAVE.toString()))
				controller.favorite();
			else
				controller.unfavorite();
			
		}
		
	}
	
	class applyListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(controller.validateFields())
				controller.update();
			else
				JOptionPane.showMessageDialog(null, controller.getFieldErrors(), "Error", JOptionPane.ERROR_MESSAGE);
			
		}
		
	}
	
	class highPriceFocus extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(highPrice.getText().equals(PLACEHOLDER.PRICEHIGH.toString()))
			{
				highPrice.setText("");
				highPrice.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(highPrice.getText().isEmpty())
			{
				highPrice.setText(PLACEHOLDER.PRICEHIGH.toString());
				highPrice.setForeground(Color.GRAY);
			}			
		}
	}
	
	class lowPriceFocus extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(lowPrice.getText().equals(PLACEHOLDER.PRICELOW.toString()))
			{
				lowPrice.setText("");
				lowPrice.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(lowPrice.getText().isEmpty())
			{
				lowPrice.setText(PLACEHOLDER.PRICELOW.toString());
				lowPrice.setForeground(Color.GRAY);
			}			
		}
	}
	
	class brandFocus extends FocusAdapter{
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
	
	class categoryFocus extends FocusAdapter{
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
	
	class rightClickListener extends MouseAdapter{
		@Override
		public void mouseReleased(MouseEvent e) {
			if(SwingUtilities.isRightMouseButton(e) && !productsTable.getSelectionModel().isSelectionEmpty())
			{
				int x = e.getX();
				int y = e.getY();
			
				controller.checkItems();
				rightClick.show(productsTable, x, y);
			}
			
		}
	}
	
	class disposeListener extends WindowAdapter{
		@Override
		public void windowClosed(WindowEvent e) {
			controller.close();
		}
	}

	public JMenuItem getRate() {
		return rate;
	}

	public void setRate(JMenuItem rate) {
		this.rate = rate;
	}

	public JMenuItem getCart() {
		return cart;
	}

	public void setCart(JMenuItem cart) {
		this.cart = cart;
	}

	public JMenuItem getFave() {
		return fave;
	}

	public void setFave(JMenuItem fave) {
		this.fave = fave;
	}

	public JMenuItem getFollow() {
		return follow;
	}

	public void setFollow(JMenuItem follow) {
		this.follow = follow;
	}

	
}
