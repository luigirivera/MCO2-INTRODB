package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.SalesController;
import model.STATUS;

public class SalesView extends JFrame{
	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelSalesTable;
	private JTable salesTable;
	private JScrollPane scrollSalesTable;
	private JComboBox<String> filter;
	private JPopupMenu rightClick;
	private JMenuItem updateStatus;
	private JLabel income, balance;
	private JButton moveToBalance;
	private SalesController controller;
	
	public SalesView()
	{
		super("Sales");
		
		setLayout(null);
		setSize(1000,400);
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
		modelSalesTable = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		salesTable = new JTable(modelSalesTable);
		scrollSalesTable = new JScrollPane(salesTable);
		
		filter = new JComboBox<String>();
		
		rightClick = new JPopupMenu();
		
		updateStatus = new JMenuItem("Update Status");
		
		income = new JLabel();
		balance = new JLabel();
		moveToBalance = new JButton("Move To Balance");
	}
	
	private void initialize()
	{
		add(filter);
		add(scrollSalesTable);
		add(income);
		add(balance);
		add(moveToBalance);
		
		income.setFont(new Font("Arial", Font.BOLD, 25));
		balance.setFont(income.getFont());
		
		filter.addItem("");
		filter.addItem(PLACEHOLDER.THISMONTH.toString());
		filter.addItem(PLACEHOLDER.LASTMONTH.toString());
		filter.addItem(PLACEHOLDER.FIVERECENT.toString());
		filter.addItem(PLACEHOLDER.TENRECENT.toString());
		filter.addItem(PLACEHOLDER.FIVEOLDEST.toString());
		filter.addItem(PLACEHOLDER.TENOLDEST.toString());
		
		rightClick.add(updateStatus);
		
		moveToBalance.setSize(200, 40);
		moveToBalance.setLocation(this.getWidth() - moveToBalance.getWidth() - 10, 0);
		
		filter.setSize(150, 40);
		filter.setLocation(0, 0);
		
		income.setSize(250, 40);
		income.setLocation(filter.getX() + filter.getWidth() + 10, 0);
		
		balance.setSize(income.getSize());
		balance.setLocation(income.getX() + income.getWidth() + 10, 0);
		
		scrollSalesTable.setSize(this.getWidth(), this.getHeight() - filter.getHeight());
		scrollSalesTable.setLocation(0, filter.getY() + filter.getHeight());
	}
	
	private void generateTable()
	{
		modelSalesTable.addColumn(PLACEHOLDER.NAME.toString());
		modelSalesTable.addColumn(PLACEHOLDER.CATEGORY.toString());
		modelSalesTable.addColumn(PLACEHOLDER.BRAND.toString());
		modelSalesTable.addColumn(PLACEHOLDER.BUYER.toString());
		modelSalesTable.addColumn(PLACEHOLDER.QUANTITY.toString());
		modelSalesTable.addColumn(PLACEHOLDER.STATUS.toString());
		modelSalesTable.addColumn(PLACEHOLDER.ORDERDATE.toString());
		modelSalesTable.addColumn(PLACEHOLDER.TOTAL.toString());
		
		salesTable.getTableHeader().setResizingAllowed(false);
		salesTable.getTableHeader().setReorderingAllowed(false);
		salesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		salesTable.setOpaque(false);
		((DefaultTableCellRenderer)salesTable.getDefaultRenderer(Object.class)).setOpaque(false);
		
		scrollSalesTable.setOpaque(false);
		scrollSalesTable.getViewport().setOpaque(false);
	}
	
	public void addController(SalesController controller) {this.controller = controller;}
	
	private void addListeners()
	{
		addWindowListener(new disposeListener());
		salesTable.addMouseListener(new rightClickListener());
		filter.addItemListener(new orderListener());
		updateStatus.addActionListener(new updateListener());
	}
	
	class updateListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JPanel panel = new JPanel();
			JComboBox<String> status = new JComboBox<String>();
			panel.setLayout(null);
			panel.add(status);
			
			status.addItem(STATUS.PENDING.toString());
			status.addItem(STATUS.ONTRANSIT.toString());
			status.addItem(STATUS.DELAYED.toString());
			status.addItem(STATUS.DELIVERED.toString());
			
			panel.setPreferredSize(new Dimension(300, 30));
			status.setPreferredSize(new Dimension(300, 30));
			status.setBounds(0, 0, 300, 30);
			
			JOptionPane.showMessageDialog(null, panel, "Update Status", JOptionPane.INFORMATION_MESSAGE);
			
			controller.updateStatus((String)status.getSelectedItem());
		}
		
	}
	
	class orderListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			controller.update();
			
		}
		
	}
	
	class rightClickListener extends MouseAdapter{
		@Override
		public void mouseReleased(MouseEvent e) {
			if(SwingUtilities.isRightMouseButton(e) && !salesTable.getSelectionModel().isSelectionEmpty())
			{
				int x = e.getX();
				int y = e.getY();
				
				rightClick.show(salesTable, x, y);
			}
			
		}
	}
	
	class disposeListener extends WindowAdapter{
		@Override
		public void windowClosed(WindowEvent e) {
			controller.dispose();
		}
	}

	public DefaultTableModel getModelSalesTable() {
		return modelSalesTable;
	}

	public void setModelSalesTable(DefaultTableModel modelSalesTable) {
		this.modelSalesTable = modelSalesTable;
	}

	public JTable getSalesTable() {
		return salesTable;
	}

	public void setSalesTable(JTable salesTable) {
		this.salesTable = salesTable;
	}

	public JComboBox<String> getFilter() {
		return filter;
	}

	public void setFilter(JComboBox<String> filter) {
		this.filter = filter;
	}

	public JMenuItem getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(JMenuItem updateStatus) {
		this.updateStatus = updateStatus;
	}

	public JLabel getIncome() {
		return income;
	}

	public void setIncome(JLabel income) {
		this.income = income;
	}

	public JLabel getBalance() {
		return balance;
	}

	public void setBalance(JLabel balance) {
		this.balance = balance;
	}
}
