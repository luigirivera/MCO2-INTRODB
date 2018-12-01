package view;

import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.OrderDetailsController;

public class OrderDetailView extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTable detailsTable;
	private DefaultTableModel modelDetailsTable;
	private JScrollPane scrollDetailsTable;
	private JComboBox<String> status;
	private JLabel subtotal;
	private OrderDetailsController controller;
	public OrderDetailView(int id)
	{
		super("Order #" + id + " Details");
		
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
		modelDetailsTable = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		detailsTable = new JTable(modelDetailsTable);
		scrollDetailsTable = new JScrollPane(detailsTable);
		
		status = new JComboBox<String>();
		subtotal = new JLabel();
	}
	
	private void initialize()
	{
		add(status);
		add(subtotal);
		add(scrollDetailsTable);
		
		subtotal.setFont(new Font("Arial", Font.BOLD, 25));
		
		status.addItem("");
		status.addItem(PLACEHOLDER.PENDING.toString());
		status.addItem(PLACEHOLDER.ONTRANSIT.toString());
		status.addItem(PLACEHOLDER.DELAYED.toString());
		status.addItem(PLACEHOLDER.DELIVERED.toString());
		
		status.setSize(100, 40);
		status.setLocation(0, 0);
		
		subtotal.setSize(250, 40);
		subtotal.setLocation(status.getX() + status.getWidth() + 10, 0);
		
		scrollDetailsTable.setSize(this.getWidth(), this.getHeight() - status.getHeight());
		scrollDetailsTable.setLocation(0, status.getY() + status.getHeight());
	}
	
	private void generateTable()
	{
		modelDetailsTable.addColumn(PLACEHOLDER.PRODUCT.toString());
		modelDetailsTable.addColumn(PLACEHOLDER.CATEGORY.toString());
		modelDetailsTable.addColumn(PLACEHOLDER.BRAND.toString());
		modelDetailsTable.addColumn(PLACEHOLDER.SELLER.toString());
		modelDetailsTable.addColumn(PLACEHOLDER.QUANTITY.toString());
		modelDetailsTable.addColumn(PLACEHOLDER.STATUS.toString());
		modelDetailsTable.addColumn(PLACEHOLDER.DELIVERYDATE.toString());
		modelDetailsTable.addColumn(PLACEHOLDER.TOTAL.toString());
		
		detailsTable.getTableHeader().setResizingAllowed(false);
		detailsTable.getTableHeader().setReorderingAllowed(false);
		detailsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		detailsTable.setOpaque(false);
		((DefaultTableCellRenderer)detailsTable.getDefaultRenderer(Object.class)).setOpaque(false);
		
		scrollDetailsTable.setOpaque(false);
		scrollDetailsTable.getViewport().setOpaque(false);
	}
	
	public void addController(OrderDetailsController controller) {this.controller = controller;}
	
	private void addListeners()
	{
		addWindowListener(new disposeListener());
		status.addItemListener(new orderListener());
	}
	
	class disposeListener extends WindowAdapter{
		@Override
		public void windowClosed(WindowEvent e) {
			controller.dispose();
		}
	}
	
	class orderListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			controller.update();
			
		}
		
	}

	public JTable getDetailsTable() {
		return detailsTable;
	}

	public void setDetailsTable(JTable detailsTable) {
		this.detailsTable = detailsTable;
	}

	public DefaultTableModel getModelDetailsTable() {
		return modelDetailsTable;
	}

	public void setModelDetailsTable(DefaultTableModel modelDetailsTable) {
		this.modelDetailsTable = modelDetailsTable;
	}

	public JComboBox<String> getStatus() {
		return status;
	}

	public void setStatus(JComboBox<String> status) {
		this.status = status;
	}

	public JLabel getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(JLabel subtotal) {
		this.subtotal = subtotal;
	}
}
