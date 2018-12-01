package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.OrdersController;

public class Orders extends JFrame {
	private static final long serialVersionUID = 1L;
	private JComboBox<String> orderFilter;
	private JTable ordersTable;
	private DefaultTableModel modelOrdersTable;
	private JScrollPane scrollOrdersTable;
	private JPopupMenu rightClick;
	private JMenuItem expand;
	private OrdersController controller;
	
	public Orders()
	{
		super("Orders");
		
		setLayout(null);
		setSize(1250,450);
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
		modelOrdersTable = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		ordersTable = new JTable(modelOrdersTable);
		
		scrollOrdersTable = new JScrollPane(ordersTable);
		
		rightClick = new JPopupMenu();
		
		expand = new JMenuItem("Expand");
		
		orderFilter = new JComboBox<String>();
	}
	
	private void initialize()
	{
		add(orderFilter);
		add(scrollOrdersTable);
		
		orderFilter.addItem("");
		orderFilter.addItem(PLACEHOLDER.NTO.toString());
		orderFilter.addItem(PLACEHOLDER.OTN.toString());
		orderFilter.addItem(PLACEHOLDER.QHTL.toString());
		orderFilter.addItem(PLACEHOLDER.QLTH.toString());
		
		rightClick.add(expand);
		
		orderFilter.setSize(100, 40);
		orderFilter.setLocation(0, 0);
		
		scrollOrdersTable.setSize(this.getWidth(), this.getHeight() - orderFilter.getHeight());
		scrollOrdersTable.setLocation(0, orderFilter.getY() + orderFilter.getHeight());
	}
	
	private void generateTable()
	{
		modelOrdersTable.addColumn(PLACEHOLDER.ORDERID.toString());
		modelOrdersTable.addColumn(PLACEHOLDER.QUANTITY.toString());
		modelOrdersTable.addColumn(PLACEHOLDER.ORDERADDRESS.toString());
		modelOrdersTable.addColumn(PLACEHOLDER.ORDERPAYMENT.toString());
		modelOrdersTable.addColumn(PLACEHOLDER.ORDERDATE.toString());
		
		ordersTable.getTableHeader().setResizingAllowed(false);
		ordersTable.getTableHeader().setReorderingAllowed(false);
		ordersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		ordersTable.setOpaque(false);
		((DefaultTableCellRenderer)ordersTable.getDefaultRenderer(Object.class)).setOpaque(false);
		
		scrollOrdersTable.setOpaque(false);
		scrollOrdersTable.getViewport().setOpaque(false);
	}
	
	public void addController(OrdersController controller) {this.controller = controller;}
	
	private void addListeners()
	{
		ordersTable.addMouseListener(new rightClickListener());
		orderFilter.addItemListener(new orderListener());
		addWindowListener(new disposeListener());
		expand.addActionListener(new expandListener());
	}
	
	class expandListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			controller.showDetails();
			
		}
		
	}
	
	class disposeListener extends WindowAdapter{
		@Override
		public void windowClosed(WindowEvent e) {
			controller.dispose();
		}
	}
	
	class rightClickListener extends MouseAdapter{
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if(SwingUtilities.isRightMouseButton(e) && !ordersTable.getSelectionModel().isSelectionEmpty())
			{
				int x = e.getX();
				int y = e.getY();
			
				
				rightClick.show(ordersTable, x, y);
			}
		}
	}
	
	class orderListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			controller.update();
			
		}
		
	}

	public JComboBox<String> getOrderFilter() {
		return orderFilter;
	}

	public void setOrderFilter(JComboBox<String> orderFilter) {
		this.orderFilter = orderFilter;
	}

	public JTable getOrdersTable() {
		return ordersTable;
	}

	public void setOrdersTable(JTable ordersTable) {
		this.ordersTable = ordersTable;
	}

	public DefaultTableModel getModelOrdersTable() {
		return modelOrdersTable;
	}

	public void setModelOrdersTable(DefaultTableModel modelOrdersTable) {
		this.modelOrdersTable = modelOrdersTable;
	}

	public JScrollPane getScrollOrdersTable() {
		return scrollOrdersTable;
	}

	public void setScrollOrdersTable(JScrollPane scrollOrdersTable) {
		this.scrollOrdersTable = scrollOrdersTable;
	}
}
