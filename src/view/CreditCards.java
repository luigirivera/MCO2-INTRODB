package view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CreditCards extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField number, CVC;
	private JButton add, save, cancel;
	private JPanel addPanel;
	private JComboBox<Integer> month, year;
	private JTable cardsTable;
	private DefaultTableModel modelCardsTable;
	private JScrollPane scrollCardsTable;
	private JPopupMenu rightClick;
	
	public CreditCards()
	{
		super("Credit Cards");
		
		setLayout(null);
		setSize(600,450);
		instantiate();
		initialize();
//		addListeners();
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void instantiate()
	{
		number = new JTextField(PLACEHOLDER.CCNUM.toString());
		CVC = new JTextField(PLACEHOLDER.CVC.toString());
		
		add = new JButton("+ Add Card");
		save = new JButton("Save");
		cancel = new JButton("Cancel");
		
		addPanel = new JPanel(null);
		
		month = new JComboBox<Integer>();
		year = new JComboBox<Integer>();
		
		modelCardsTable = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		cardsTable = new JTable(modelCardsTable);
		scrollCardsTable = new JScrollPane(cardsTable);
		
		rightClick = new JPopupMenu();
	}
	
	private void initialize()
	{
		
		addPanel.add(number);
		addPanel.add(month);
		addPanel.add(year);
		addPanel.add(CVC);
		addPanel.add(save);
		addPanel.add(cancel);
		add(add);
		add(addPanel);

		add(scrollCardsTable);
		
		add.setSize(100, 40);
		add.setLocation(this.getWidth() - add.getWidth(), 10);
		
		addPanel.setSize(this.getWidth(), add.getHeight());
		addPanel.setLocation(10, 10);
		
		number.setSize(150, addPanel.getHeight());
		number.setLocation(0, 0);
		
		CVC.setSize(50, number.getHeight());
		CVC.setLocation(number.getX() + number.getWidth(), number.getY());
		
		month.setSize(100,CVC.getHeight());
		month.setLocation(CVC.getX() + CVC.getWidth(), CVC.getY());
		
		year.setSize(month.getSize());
		year.setLocation(month.getX() + month.getWidth(), month.getY());
		
		save.setSize(year.getSize());
		save.setLocation(year.getX() + year.getWidth(), year.getY());
		
		cancel.setSize(save.getSize());
		cancel.setLocation(save.getX() + save.getWidth(), save.getY());
	}
}
