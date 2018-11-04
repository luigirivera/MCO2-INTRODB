package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class BankAccounts extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JTextField bank, accountNumber;
	private JButton save, add;
	private JPanel addPanel;
	private JTable banksTable;
	
	public BankAccounts()
	{
		super("Bank Accounts");
		
		setLayout(null);
		setSize(600,450);
//		instantiate();
//		initialize();
//		addListeners();
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}
