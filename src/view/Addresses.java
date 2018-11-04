package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Addresses extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton add, save;
	private JPanel addPanel;
	private JTextField line1, line2, city, province, zip;
	private JTable addressTable;
	
	public Addresses()
	{
		super("Addresses");
		
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
