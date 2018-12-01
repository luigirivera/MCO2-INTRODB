package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
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

import controller.AccountsController;

public class Accounts extends JFrame {
	private static final long serialVersionUID = 1L;
	private JComboBox<String> filter;
	private JButton add;
	private JTable accountsTable;
	private DefaultTableModel modelAccountsTable;
	private JScrollPane scrollAccountsTable;
	private AccountsController controller;
	private JPopupMenu rightClick;
	private JMenuItem unlock, delete;
	
	public Accounts()
	{
		super("Accounts");
		
		setLayout(null);
		setSize(1250,450);
		instantiate();
		initialize();
		generateTable();
		addListener();
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void instantiate()
	{
		add = new JButton(PLACEHOLDER.ADD.toString());
		
		modelAccountsTable = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		accountsTable = new JTable(modelAccountsTable);
		
		scrollAccountsTable = new JScrollPane(accountsTable);
		
		filter = new JComboBox<String>();
		
		rightClick = new JPopupMenu();
		delete = new JMenuItem(PLACEHOLDER.DELETE.toString());
		unlock = new JMenuItem(PLACEHOLDER.UNLOCK.toString());
	}
	
	private void initialize()
	{
		add(add);
		add(filter);
		
		filter.addItem("");
		filter.addItem(PLACEHOLDER.CONSUMER.toString());
		filter.addItem(PLACEHOLDER.CORPORATE.toString());
		filter.addItem(PLACEHOLDER.LOCKED.toString());
		filter.addItem(PLACEHOLDER.DELETION.toString());
		add(scrollAccountsTable);
		
		filter.setSize(100, 40);
		filter.setLocation(0, 0);
		
		add.setSize(filter.getSize());
		add.setLocation(this.getWidth() - add.getWidth() - 10, 0);
		
		scrollAccountsTable.setSize(this.getWidth(), this.getHeight() - add.getHeight());
		scrollAccountsTable.setLocation(0, add.getY() + add.getHeight());
	}
	
	private void generateTable()
	{
		modelAccountsTable.addColumn(PLACEHOLDER.USERNAME.toString());
		modelAccountsTable.addColumn(PLACEHOLDER.NUMBER.toString());
		modelAccountsTable.addColumn(PLACEHOLDER.EMAIL.toString());
		modelAccountsTable.addColumn(PLACEHOLDER.REGISTERDATE.toString());
		modelAccountsTable.addColumn(PLACEHOLDER.LASTLOGIN.toString());
		
		accountsTable.getTableHeader().setResizingAllowed(false);
		accountsTable.getTableHeader().setReorderingAllowed(false);
		accountsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		accountsTable.setOpaque(false);
		((DefaultTableCellRenderer)accountsTable.getDefaultRenderer(Object.class)).setOpaque(false);
		
		scrollAccountsTable.setOpaque(false);
		scrollAccountsTable.getViewport().setOpaque(false);
	}
	
	public void addController(AccountsController controller) { this.controller = controller; }
	
	private void addListener()
	{
		addWindowListener(new disposeListener());
		accountsTable.addMouseListener(new rightClickListener());
		filter.addActionListener(new filterListener());
		unlock.addActionListener(new unlockListener());
		delete.addActionListener(new deleteListener());
		add.addActionListener(new addListener());
	}
	
	class addListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.addCorporate();
			
		}
	}
	
	class deleteListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			controller.delete();
			
		}
		
	}
	
	class unlockListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			controller.unlock();
			
		}
		
	}
	
	class filterListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.update();
			
		}
		
	}
	
	class rightClickListener extends MouseAdapter{
		@Override
		public void mouseReleased(MouseEvent e) {
			if(SwingUtilities.isRightMouseButton(e) && !accountsTable.getSelectionModel().isSelectionEmpty())
			{
				int x = e.getX();
				int y = e.getY();
				
				controller.setMenuItems();
				rightClick.show(accountsTable, x, y);
			}
			
		}
	}
	
	class disposeListener extends WindowAdapter{
		@Override
		public void windowClosed(WindowEvent e) {
			controller.close();
		}
	}

	public JComboBox<String> getFilter() {
		return filter;
	}

	public void setFilter(JComboBox<String> filter) {
		this.filter = filter;
	}

	public JTable getAccountsTable() {
		return accountsTable;
	}

	public void setAccountsTable(JTable accountsTable) {
		this.accountsTable = accountsTable;
	}

	public DefaultTableModel getModelAccountsTable() {
		return modelAccountsTable;
	}

	public void setModelAccountsTable(DefaultTableModel modelAccountsTable) {
		this.modelAccountsTable = modelAccountsTable;
	}

	public JPopupMenu getRightClick() {
		return rightClick;
	}

	public void setRightClick(JPopupMenu rightClick) {
		this.rightClick = rightClick;
	}

	public JMenuItem getUnlock() {
		return unlock;
	}

	public void setUnlock(JMenuItem unlock) {
		this.unlock = unlock;
	}

	public JMenuItem getDelete() {
		return delete;
	}

	public void setDelete(JMenuItem delete) {
		this.delete = delete;
	}
	
	
}
