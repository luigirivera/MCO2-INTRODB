package view;

import javax.swing.JFrame;

public class BankAccounts extends JFrame {
	private static final long serialVersionUID = 1L;

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
