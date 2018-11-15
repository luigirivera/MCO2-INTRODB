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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.BanksController;

public class BankAccounts extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JTextField bank, accountNumber;
	private JButton save, add, cancel;
	private JPanel addPanel;
	private JTable banksTable;
	private DefaultTableModel modelBanksTable;
	private JScrollPane scrollBanksTable;
	private JPopupMenu rightClick;
	private JMenuItem delete;
	private BanksController controller;
	
	public BankAccounts()
	{
		super("Bank Accounts");
		
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
		bank = new JTextField(PLACEHOLDER.BANK.toString());
		accountNumber = new JTextField(PLACEHOLDER.ACCNUM.toString());
		
		save = new JButton(PLACEHOLDER.SAVE.toString());
		add = new JButton(PLACEHOLDER.ADD.toString());
		cancel = new JButton(PLACEHOLDER.CANCEL.toString());
		
		addPanel = new JPanel(null);
		
		modelBanksTable = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		banksTable = new JTable(modelBanksTable);
		
		scrollBanksTable = new JScrollPane(banksTable);
		
		rightClick = new JPopupMenu();
		delete = new JMenuItem("Delete");
	}
	
	private void initialize()
	{
		add(addPanel);
		addPanel.add(bank);
		addPanel.add(accountNumber);
		addPanel.add(save);
		addPanel.add(cancel);
		add(add);
		add(scrollBanksTable);
		
		rightClick.add(delete);
		
		addPanel.setVisible(false);
		
		Color fg = Color.GRAY;
		
		bank.setForeground(fg);
		accountNumber.setForeground(fg);
		
		
		add.setSize(100, 40);
		add.setLocation(this.getWidth() - add.getWidth() - 10, 10);
		
		addPanel.setSize(this.getWidth() - add.getWidth() - 10, add.getHeight());
		addPanel.setLocation(10, 10);
		
		bank.setSize(addPanel.getWidth()/4, addPanel.getHeight());
		bank.setLocation(0, 0);
		
		accountNumber.setSize(bank.getSize());
		accountNumber.setLocation(bank.getX() + bank.getWidth(), bank.getY());
		
		save.setSize(accountNumber.getSize());
		save.setLocation(accountNumber.getX() + accountNumber.getWidth(), accountNumber.getY());
		
		cancel.setSize(save.getSize());
		cancel.setLocation(save.getX() + save.getWidth(), save.getY());
		
		scrollBanksTable.setSize(this.getWidth(), this.getHeight() - addPanel.getHeight());
		scrollBanksTable.setLocation(0, addPanel.getY() + addPanel.getHeight());
	}
	
	public void addController(BanksController controller)
	{
		this.controller = controller;
	}
	
	private void generateTable()
	{
		modelBanksTable.addColumn(PLACEHOLDER.BANK.toString());
		modelBanksTable.addColumn(PLACEHOLDER.ACCNUM.toString());
		
		banksTable.getTableHeader().setResizingAllowed(false);
		banksTable.getTableHeader().setReorderingAllowed(false);
		banksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		banksTable.setOpaque(false);
		((DefaultTableCellRenderer)banksTable.getDefaultRenderer(Object.class)).setOpaque(false);
		
		scrollBanksTable.setOpaque(false);
		scrollBanksTable.getViewport().setOpaque(false);
	}
	
	private void addListeners()
	{
		bank.addFocusListener(new bankFocus());
		accountNumber.addFocusListener(new accNumFocus());
		addWindowListener(new disposeListener());
		add.addActionListener(new addListener());
		save.addActionListener(new doneListener());
		cancel.addActionListener(new doneListener());
		banksTable.addMouseListener(new rightClickListener());
		delete.addActionListener(new deleteListener());
	}
	
	class deleteListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.delete();
			
		}
		
	}

	class rightClickListener extends MouseAdapter{
		@Override
		public void mouseReleased(MouseEvent e) {
			if(SwingUtilities.isRightMouseButton(e) && !banksTable.getSelectionModel().isSelectionEmpty())
			{
				int x = e.getX();
				int y = e.getY();
			
				rightClick.show(banksTable, x, y);
			}
			
		}
	}
	
	class doneListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(save))
				if(controller.validateBankAccount() && !controller.doesBankAccountExist())
				{
					controller.saveBankAccount();
					controller.clear();
				}
				else
					JOptionPane.showMessageDialog(null, controller.getBankAccountInputErrors().concat(controller.getBankAccountExistError()), "Error", JOptionPane.ERROR_MESSAGE);
			else
				controller.clear();
			
		}
		
	}
	
	class disposeListener extends WindowAdapter{
		@Override
		public void windowClosed(WindowEvent e) {
			controller.close();
		}
	}
	
	class addListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			add.setEnabled(false);
			addPanel.setVisible(true);
		}
		
	}
	
	class bankFocus extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(bank.getText().equals(PLACEHOLDER.BANK.toString()))
			{
				bank.setText("");
				bank.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(bank.getText().isEmpty())
			{
				bank.setText(PLACEHOLDER.BANK.toString());
				bank.setForeground(Color.GRAY);
			}			
		}
	}
	
	class accNumFocus extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(accountNumber.getText().equals(PLACEHOLDER.ACCNUM.toString()))
			{
				accountNumber.setText("");
				accountNumber.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(accountNumber.getText().isEmpty())
			{
				accountNumber.setText(PLACEHOLDER.ACCNUM.toString());
				accountNumber.setForeground(Color.GRAY);
			}			
		}
	}

	public JTextField getBank() {
		return bank;
	}

	public void setBank(JTextField bank) {
		this.bank = bank;
	}

	public JTextField getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(JTextField accountNumber) {
		this.accountNumber = accountNumber;
	}

	public JButton getSave() {
		return save;
	}

	public void setSave(JButton save) {
		this.save = save;
	}

	public JButton getAdd() {
		return add;
	}

	public void setAdd(JButton add) {
		this.add = add;
	}

	public JButton getCancel() {
		return cancel;
	}

	public void setCancel(JButton cancel) {
		this.cancel = cancel;
	}

	public JPanel getAddPanel() {
		return addPanel;
	}

	public void setAddPanel(JPanel addPanel) {
		this.addPanel = addPanel;
	}

	public JTable getBanksTable() {
		return banksTable;
	}

	public void setBanksTable(JTable banksTable) {
		this.banksTable = banksTable;
	}

	public DefaultTableModel getModelBanksTable() {
		return modelBanksTable;
	}

	public void setModelBanksTable(DefaultTableModel modelBanksTable) {
		this.modelBanksTable = modelBanksTable;
	}

	public JScrollPane getScrollBanksTable() {
		return scrollBanksTable;
	}

	public void setScrollBanksTable(JScrollPane scrollBanksTable) {
		this.scrollBanksTable = scrollBanksTable;
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

	public BanksController getController() {
		return controller;
	}

	public void setController(BanksController controller) {
		this.controller = controller;
	}
	
	
}
