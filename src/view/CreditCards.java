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
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
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

import controller.CreditCardsController;

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
	private JMenuItem delete;
	private CreditCardsController controller;
	
	public CreditCards()
	{
		super("Credit Cards");
		
		setLayout(null);
		setSize(750,450);
		instantiate();
		initialize();
		generateTable();
		addListeners();
		configure();
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void instantiate()
	{
		number = new JTextField(PLACEHOLDER.CCNUM.toString());
		CVC = new JTextField(PLACEHOLDER.CVC.toString());
		
		save = new JButton(PLACEHOLDER.SAVE.toString());
		add = new JButton(PLACEHOLDER.ADD.toString());
		cancel = new JButton(PLACEHOLDER.CANCEL.toString());
		
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
		
		delete = new JMenuItem(PLACEHOLDER.DELETE.toString());
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

		addPanel.setVisible(false);
		add(scrollCardsTable);
		
		rightClick.add(delete);
		
		number.setForeground(Color.GRAY);
		CVC.setForeground(Color.GRAY);
		
		add.setSize(100, 40);
		add.setLocation(this.getWidth() - add.getWidth() - 10, 10);
		
		addPanel.setSize(this.getWidth() - add.getWidth() - 10, add.getHeight());
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
		
		scrollCardsTable.setSize(this.getWidth(), this.getHeight() - addPanel.getHeight());
		scrollCardsTable.setLocation(0, addPanel.getY() + addPanel.getHeight());
	}
	
	private void generateTable()
	{
		modelCardsTable.addColumn(PLACEHOLDER.CCNUM.toString());
		modelCardsTable.addColumn("Is CVC Saved?");
		modelCardsTable.addColumn("Expiry");
		
		cardsTable.getTableHeader().setResizingAllowed(false);
		cardsTable.getTableHeader().setReorderingAllowed(false);
		cardsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		cardsTable.setOpaque(false);
		((DefaultTableCellRenderer)cardsTable.getDefaultRenderer(Object.class)).setOpaque(false);
		
		scrollCardsTable.setOpaque(false);
		scrollCardsTable.getViewport().setOpaque(false);
		
	}
	
	private void configure()
	{
		for(int i = 1; i <= 12; i++) month.addItem(i);
		
		for(int i = Calendar.getInstance().get(Calendar.YEAR); i <= Calendar.getInstance().get(Calendar.YEAR)+10 ; i++)
			year.addItem(i);
		
		
		month.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));
		year.setSelectedIndex(0);
	}
	
	
	public void addController(CreditCardsController controller)
	{
		this.controller = controller;
	}
	
	private void addListeners()
	{
		number.addFocusListener(new numberFocus());
		CVC.addFocusListener(new cvcFocus());
		add.addActionListener(new addListener());
		save.addActionListener(new doneListener());
		cancel.addActionListener(new doneListener());
		addWindowListener(new disposeListener());
		cardsTable.addMouseListener(new rightClickListener());
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
			if(SwingUtilities.isRightMouseButton(e) && !cardsTable.getSelectionModel().isSelectionEmpty())
			{
				int x = e.getX();
				int y = e.getY();
			
				rightClick.show(cardsTable, x, y);
			}
			
		}
	}
	
	class disposeListener extends WindowAdapter{
		@Override
		public void windowClosed(WindowEvent e) {
			controller.close();
		}
	}
	
	class doneListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(save))
				if(controller.validateCard() && !controller.doesCardExist())
				{
					controller.saveCard();
					controller.clear();
				}
				else
					JOptionPane.showMessageDialog(null, controller.getCardInputErrors().concat(controller.getCardExistError()), "Error", JOptionPane.ERROR_MESSAGE);
			else
				controller.clear();
			
		}
		
	}
	
	class addListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			add.setEnabled(false);
			addPanel.setVisible(true);
		}
		
	}
	
	class numberFocus extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(number.getText().equals(PLACEHOLDER.CCNUM.toString()))
			{
				number.setText("");
				number.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(number.getText().isEmpty())
			{
				number.setText(PLACEHOLDER.CCNUM.toString());
				number.setForeground(Color.GRAY);
			}			
		}
	}
	
	class cvcFocus extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(CVC.getText().equals(PLACEHOLDER.CVC.toString()))
			{
				CVC.setText("");
				CVC.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(CVC.getText().isEmpty())
			{
				CVC.setText(PLACEHOLDER.CVC.toString());
				CVC.setForeground(Color.GRAY);
			}			
		}
	}

	public JTextField getNumber() {
		return number;
	}

	public JTextField getCVC() {
		return CVC;
	}

	public JButton getAdd() {
		return add;
	}

	public JButton getSave() {
		return save;
	}

	public JButton getCancel() {
		return cancel;
	}

	public JPanel getAddPanel() {
		return addPanel;
	}

	public JComboBox<Integer> getMonth() {
		return month;
	}

	public JComboBox<Integer> getYear() {
		return year;
	}

	public JTable getCardsTable() {
		return cardsTable;
	}

	public DefaultTableModel getModelCardsTable() {
		return modelCardsTable;
	}

	public JScrollPane getScrollCardsTable() {
		return scrollCardsTable;
	}

	public JPopupMenu getRightClick() {
		return rightClick;
	}

	public JMenuItem getDelete() {
		return delete;
	}

	public CreditCardsController getController() {
		return controller;
	}
	
	
}
