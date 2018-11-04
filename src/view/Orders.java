package view;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTable;

import model.STATUS;

public class Orders extends JFrame {
	private static final long serialVersionUID = 1L;
	private JComboBox<STATUS> statusFilter;
	private JComboBox<String> order, countFilter;
	private JTable ordersTable;
	public Orders()
	{
		super("Orders");
		
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
