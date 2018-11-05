package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

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
//		instantiate();
//		initialize();
//		generateTable();
//		addListeners();
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}
