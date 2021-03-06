package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
		modelProductsTable.addColumn(PLACEHOLDER.RATING.toString());
		modelProductsTable.addColumn(PLACEHOLDER.STOCK.toString());
		modelProductsTable.addColumn(PLACEHOLDER.PRICE.toString());
		modelProductsTable.addColumn(PLACEHOLDER.DISCOUNT.toString());
		modelProductsTable.addColumn(PLACEHOLDER.SHIPPING.toString());
		modelProductsTable.addColumn(PLACEHOLDER.SHIPPINGDUR.toString());
		
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
		follow.addActionListener(new followListener());
		cart.addActionListener(new cartListener());
		rate.addActionListener(new rateListener());
		expand.addActionListener(new expandListener());
		order.addItemListener(new orderListener());
	}
	
	class orderListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			controller.update();
			
		}
		
	}
	
	class expandListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.showRatings();
			
		}
		
	}
	
	class rateListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JPanel panel = new JPanel();
			JComboBox<Integer> rating = new JComboBox<Integer>();
			JTextField rateD = new JTextField(PLACEHOLDER.RATE.toString());
			
			for(int i = 1; i<=5; i++) rating.addItem(i);
			rateD.setForeground(Color.GRAY);
			panel.add(rating);
			panel.add(rateD);
			panel.setPreferredSize(new Dimension(300, 30));
			rateD.setPreferredSize(new Dimension(250,30));
			rateD.addFocusListener(new FocusAdapter() {
				public void focusGained(FocusEvent e) {
					if(rateD.getText().equals(PLACEHOLDER.RATE.toString()))
					{
						rateD.setText("");
						rateD.setForeground(Color.BLACK);
					}
				}
				
				public void focusLost(FocusEvent e) {
					if(rateD.getText().isEmpty())
					{
						rateD.setText(PLACEHOLDER.RATE.toString());
						rateD.setForeground(Color.GRAY);
					}			
				}
			});
			
			int result = JOptionPane.showConfirmDialog(null, panel, "Rate Product", JOptionPane.OK_CANCEL_OPTION);
			if(result == JOptionPane.OK_OPTION)
				controller.rate((Integer)rating.getSelectedItem(), rateD.getText());
			
		}
		
	}
	
	class cartListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JPanel panel = new JPanel();
			JTextField quantity = new JTextField(PLACEHOLDER.QUANTITY.toString());
			quantity.setForeground(Color.GRAY);
			quantity.addFocusListener(new FocusAdapter() {
				public void focusGained(FocusEvent e) {
					if(quantity.getText().equals(PLACEHOLDER.QUANTITY.toString()))
					{
						quantity.setText("");
						quantity.setForeground(Color.BLACK);
					}
				}
				
				public void focusLost(FocusEvent e) {
					if(quantity.getText().isEmpty())
					{
						quantity.setText(PLACEHOLDER.QUANTITY.toString());
						quantity.setForeground(Color.GRAY);
					}			
				}
			});
			
			panel.add(quantity);
			panel.setPreferredSize(new Dimension(300, 30));
			quantity.setPreferredSize(new Dimension(300,30));
			int result = JOptionPane.showConfirmDialog(null, panel, "Add to Cart", JOptionPane.OK_CANCEL_OPTION);
			if(result == JOptionPane.OK_OPTION)
			{
				if(!quantity.getText().trim().isEmpty() && !quantity.getText().equals(PLACEHOLDER.QUANTITY.toString()))
				{
					try
					{
						if(Integer.parseInt(quantity.getText()) <= 0)
							throw new NumberFormatException();
						else
							if(controller.checkQuantityError(Integer.parseInt(quantity.getText())).isEmpty())
								controller.addToCart(Integer.parseInt(quantity.getText()));
							else
								JOptionPane.showMessageDialog(null, controller.checkQuantityError(Integer.parseInt(quantity.getText())), "Error", JOptionPane.ERROR_MESSAGE);
					}catch(NumberFormatException e)
					{
						JOptionPane.showMessageDialog(null, "Please enter a quantity amount", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Please enter a quantity amount", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
	}
	
	class followListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(follow.getText().equals(PLACEHOLDER.FOLLOW.toString()))
				controller.follow();
			else
				controller.unfollow();
			
		}
		
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
