package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.SellerProductsController;

public class SellerProducts extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JButton add;
	private JPopupMenu rightClick;
	private JMenuItem delete, edit;
	private JTable productsTable;
	private DefaultTableModel modelProductsTable;
	private JScrollPane scrollProductsTable;
	private SellerProductsController controller;
	
	public SellerProducts()
	{
		super("Products");
		
		setLayout(null);
		setSize(1450,450);
		instantiate();
		initialize();
		generateTable();
		addListeners();
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void instantiate()
	{
		add = new JButton(PLACEHOLDER.ADD.toString());
		
		rightClick = new JPopupMenu();
		delete = new JMenuItem(PLACEHOLDER.DELETE.toString());
		edit = new JMenuItem("Edit");
		
		modelProductsTable = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		productsTable = new JTable(modelProductsTable);
		
		scrollProductsTable = new JScrollPane(productsTable);
	}
	
	private void initialize()
	{
		add(add);
		add(scrollProductsTable);
		
		rightClick.add(edit);
		rightClick.add(delete);
		
		add.setSize(100, 40);
		add.setLocation(this.getWidth() - add.getWidth() - 10, 0);
		
		scrollProductsTable.setSize(this.getWidth(), this.getHeight() - add.getHeight());
		scrollProductsTable.setLocation(0, add.getY() + add.getHeight());
	}
	
	private void generateTable()
	{
		modelProductsTable.addColumn(PLACEHOLDER.NAME.toString());
		modelProductsTable.addColumn(PLACEHOLDER.CATEGORY.toString());
		modelProductsTable.addColumn(PLACEHOLDER.BRAND.toString());
		modelProductsTable.addColumn(PLACEHOLDER.DESCRIPTION.toString());
		modelProductsTable.addColumn(PLACEHOLDER.FAVORITES.toString());
		modelProductsTable.addColumn(PLACEHOLDER.RATING.toString());
		modelProductsTable.addColumn(PLACEHOLDER.STOCK.toString());
		modelProductsTable.addColumn(PLACEHOLDER.SOLD.toString());
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
	
	public void addController(SellerProductsController controller) { this.controller = controller;}
	
	private void addListeners()
	{
		add.addActionListener(new addListener());
		productsTable.addMouseListener(new rightClickListener());
		edit.addActionListener(new menuListener());
		delete.addActionListener(new menuListener());
		addWindowListener(new disposeListener());
	}
	
	class menuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(arg0.getSource().equals(delete))
				controller.delete();
			else
			{				
				controller.getData();
				controller.toggleProductInfo();
				controller.setData();
			}
			
		}
		
	}
	
	class addListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.toggleProductInfo();
			
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

	public JButton getAdd() {
		return add;
	}

	public void setAdd(JButton add) {
		this.add = add;
	}

	public JPopupMenu getRightClick() {
		return rightClick;
	}

	public void setRightClick(JPopupMenu rightClick) {
		this.rightClick = rightClick;
	}

	public JMenuItem getDelete() {
		return delete;
	}

	public void setDelete(JMenuItem delete) {
		this.delete = delete;
	}

	public JMenuItem getEdit() {
		return edit;
	}

	public void setEdit(JMenuItem edit) {
		this.edit = edit;
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
