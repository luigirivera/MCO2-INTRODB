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

import controller.CartController;

public class CartView extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JButton checkout;
	private JPopupMenu rightClick;
	private JMenuItem delete, editquantity;
	private JTable cartTable;
	private DefaultTableModel modelCartTable;
	private JScrollPane scrollCartTable;
	private CartController controller;
	
	public CartView()
	{
		super("Cart");
		
		setLayout(null);
		setSize(750,450);
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
		
		checkout = new JButton("Check Out");
		
		rightClick = new JPopupMenu();
		
		delete = new JMenuItem(PLACEHOLDER.DELETE.toString());
		editquantity = new JMenuItem("Edit");
		
		modelCartTable = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		cartTable = new JTable(modelCartTable);
		
		scrollCartTable = new JScrollPane(cartTable);
	}
	
	private void initialize()
	{
		add(checkout);
		add(scrollCartTable);
		
		rightClick.add(delete);
		rightClick.add(editquantity);
		
		checkout.setSize(100, 40);
		checkout.setLocation(this.getWidth() - checkout.getWidth() - 10, 0);
		
		scrollCartTable.setSize(this.getWidth(), this.getHeight() - checkout.getHeight());
		scrollCartTable.setLocation(0, checkout.getY() + checkout.getHeight());
	}
	
	private void generateTable()
	{
		modelCartTable.addColumn(PLACEHOLDER.NAME.toString());
		modelCartTable.addColumn(PLACEHOLDER.QUANTITY.toString());
		modelCartTable.addColumn(PLACEHOLDER.DISCOUNT.toString());
		modelCartTable.addColumn(PLACEHOLDER.PRICE.toString());
		modelCartTable.addColumn(PLACEHOLDER.SHIPPING.toString());
		modelCartTable.addColumn("Total");
		
		cartTable.getTableHeader().setResizingAllowed(false);
		cartTable.getTableHeader().setReorderingAllowed(false);
		cartTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		cartTable.setOpaque(false);
		((DefaultTableCellRenderer)cartTable.getDefaultRenderer(Object.class)).setOpaque(false);
		
		scrollCartTable.setOpaque(false);
		scrollCartTable.getViewport().setOpaque(false);
	}
	
	public void addController(CartController controller) {this.controller = controller;}
	
	private void addListeners()
	{
		addWindowListener(new disposeListener());
		delete.addActionListener(new deleteListener());
		cartTable.addMouseListener(new rightClickListener());
		checkout.addActionListener(new checkoutListener());
	}
	
	class rightClickListener extends MouseAdapter{
		@Override
		public void mouseReleased(MouseEvent e) {
			if(SwingUtilities.isRightMouseButton(e) && !cartTable.getSelectionModel().isSelectionEmpty())
			{
				int x = e.getX();
				int y = e.getY();
			
				rightClick.show(cartTable, x, y);
			}
			
		}
	}
	
	class deleteListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.delete();
			
		}
		
	}
	
	class disposeListener extends WindowAdapter{
		@Override
		public void windowClosed(WindowEvent e) {
			controller.close();
		}
	}

	public JButton getCheckout() {
		return checkout;
	}

	public void setCheckout(JButton checkout) {
		this.checkout = checkout;
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

	public JMenuItem getEditquantity() {
		return editquantity;
	}

	public void setEditquantity(JMenuItem editquantity) {
		this.editquantity = editquantity;
	}

	public JTable getCartTable() {
		return cartTable;
	}

	public void setCartTable(JTable cartTable) {
		this.cartTable = cartTable;
	}

	public DefaultTableModel getModelCartTable() {
		return modelCartTable;
	}

	public void setModelCartTable(DefaultTableModel modelCartTable) {
		this.modelCartTable = modelCartTable;
	}

	public JScrollPane getScrollCartTable() {
		return scrollCartTable;
	}

	public void setScrollCartTable(JScrollPane scrollCartTable) {
		this.scrollCartTable = scrollCartTable;
	}

	public CartController getController() {
		return controller;
	}

	public void setController(CartController controller) {
		this.controller = controller;
	}

}
