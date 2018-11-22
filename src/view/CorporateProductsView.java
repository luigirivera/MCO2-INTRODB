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

import controller.CorporateProductsController;
import view.ConsumerProductsView.applyListener;

public class CorporateProductsView extends ProductsView {
	private static final long serialVersionUID = 1L;
	private JMenuItem delete;
	private CorporateProductsController controller;

	public CorporateProductsView()
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
		delete = new JMenuItem(PLACEHOLDER.DELETE.toString());
	}
	
	private void initialize()
	{
		rightClick.add(delete);
	}
	
	private void generateTable()
	{
		modelProductsTable.addColumn(PLACEHOLDER.NAME.toString());
		modelProductsTable.addColumn(PLACEHOLDER.CATEGORY.toString());
		modelProductsTable.addColumn(PLACEHOLDER.BRAND.toString());
		modelProductsTable.addColumn(PLACEHOLDER.SELLER.toString());
		modelProductsTable.addColumn(PLACEHOLDER.DESCRIPTION.toString());
		modelProductsTable.addColumn(PLACEHOLDER.STOCK.toString());
		modelProductsTable.addColumn(PLACEHOLDER.SOLD.toString());
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
	
	public void addController(CorporateProductsController controller) { this.controller = controller; }
	
	private void addListeners()
	{
		delete.addActionListener(new deleteListener());
		addWindowListener(new disposeListener());
		productsTable.addMouseListener(new rightClickListener());
		category.addFocusListener(new categoryFocus());
		brand.addFocusListener(new brandFocus());
		lowPrice.addFocusListener(new lowPriceFocus());
		highPrice.addFocusListener(new highPriceFocus());
		apply.addActionListener(new applyListener());
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
	
	class deleteListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.delete();
			
		}
		
	}
}
