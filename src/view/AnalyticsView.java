package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.AnalyticsController;

public class AnalyticsView extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField category;
	private JComboBox<String> quarter;
	private JComboBox<Integer> year;
	private JButton apply;
	private DefaultTableModel modelAnalyticsTable;
	private JTable analyticsTable;
	private JScrollPane scrollAnalyticsTable;
	private AnalyticsController controller;
	
	public AnalyticsView()
	{
		super("Analytics");
		setLayout(null);
		setSize(750,400);
		instantiate();
		initialize();
		generateTable();
		addListeners();
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void instantiate()
	{
		modelAnalyticsTable = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		analyticsTable = new JTable(modelAnalyticsTable);
		scrollAnalyticsTable = new JScrollPane(analyticsTable);
		
		category = new JTextField(PLACEHOLDER.CATEGORY.toString());
		apply = new JButton("Apply");
		
		quarter = new JComboBox<String>();
		year = new JComboBox<Integer>();
	}
	
	private void initialize()
	{
		add(category);
		add(quarter);
		add(year);
		add(apply);
		add(scrollAnalyticsTable);
		
		category.setForeground(Color.GRAY);
		
		for(int i = 1; i <= 4; i++)
			quarter.addItem("Q" + i);
		
		for(int i = Calendar.getInstance().get(Calendar.YEAR); i >= Calendar.getInstance().get(Calendar.YEAR) - 15; i--)
			year.addItem(i);
		
		category.setSize(150, 40);
		category.setLocation(0, 0);
		
		quarter.setSize(50, category.getHeight());
		quarter.setLocation(category.getX() + category.getWidth() + 10, category.getY());
		
		year.setSize(100, quarter.getHeight());
		year.setLocation(quarter.getX() + quarter.getWidth() + 10, quarter.getY());
		
		apply.setSize(year.getSize());
		apply.setLocation(year.getX() + year.getWidth() + 10, year.getY());
		
		scrollAnalyticsTable.setSize(this.getWidth(), this.getHeight() - apply.getHeight());
		scrollAnalyticsTable.setLocation(0, apply.getY() + apply.getHeight());
		
	}
	
	private void generateTable()
	{
		modelAnalyticsTable.addColumn("");
		modelAnalyticsTable.addColumn("Month 1");
		modelAnalyticsTable.addColumn("Month 2");
		modelAnalyticsTable.addColumn("Month 3");
		
		analyticsTable.getTableHeader().setResizingAllowed(false);
		analyticsTable.getTableHeader().setReorderingAllowed(false);
		analyticsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		analyticsTable.setOpaque(false);
		((DefaultTableCellRenderer)analyticsTable.getDefaultRenderer(Object.class)).setOpaque(false);
		
		scrollAnalyticsTable.setOpaque(false);
		scrollAnalyticsTable.getViewport().setOpaque(false);
	}
	public void addController(AnalyticsController controller) {this.controller = controller;}
	
	private void addListeners()
	{
		addWindowListener(new disposeListener());
		quarter.addItemListener(new updateListener());
		year.addItemListener(new updateListener());
		apply.addActionListener(new updateListener());
		category.addFocusListener(new categoryFocus());
	}
	
	class updateListener implements ItemListener, ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(!category.getText().equals(PLACEHOLDER.CATEGORY.toString()) && !category.getText().trim().isEmpty())
				controller.update();
			else
				JOptionPane.showMessageDialog(null, "Please enter a catagory to show analytics from", "Error", JOptionPane.ERROR_MESSAGE);
		}

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			if(!category.getText().equals(PLACEHOLDER.CATEGORY.toString()) && !category.getText().trim().isEmpty())
				controller.update();
			
		}
		
	}
	
	class disposeListener extends WindowAdapter{
		@Override
		public void windowClosed(WindowEvent e) {
			controller.dispose();
		}
	}
	
	class categoryFocus extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(category.getText().equals(PLACEHOLDER.CATEGORY.toString()))
			{
				category.setText("");
				category.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(category.getText().isEmpty())
			{
				category.setText(PLACEHOLDER.CATEGORY.toString());
				category.setForeground(Color.GRAY);
			}			
		}
	}

	public JTextField getCategory() {
		return category;
	}

	public void setCategory(JTextField category) {
		this.category = category;
	}

	public JComboBox<String> getQuarter() {
		return quarter;
	}

	public void setQuarter(JComboBox<String> quarter) {
		this.quarter = quarter;
	}

	public JComboBox<Integer> getYear() {
		return year;
	}

	public void setYear(JComboBox<Integer> year) {
		this.year = year;
	}

	public DefaultTableModel getModelAnalyticsTable() {
		return modelAnalyticsTable;
	}

	public void setModelAnalyticsTable(DefaultTableModel modelAnalyticsTable) {
		this.modelAnalyticsTable = modelAnalyticsTable;
	}

	public JTable getAnalyticsTable() {
		return analyticsTable;
	}

	public void setAnalyticsTable(JTable analyticsTable) {
		this.analyticsTable = analyticsTable;
	}
}
