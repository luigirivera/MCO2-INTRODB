package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.RatingsController;
import view.ConsumerProductsView.disposeListener;

public class RatingView extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTable ratingsTable;
	private DefaultTableModel modelRatingsTable;
	private JScrollPane scrollRatingsTable;
	private String name;
	private JPopupMenu rightClick;
	private JMenuItem delete;
	private RatingsController controller;
	
	public RatingView(String product)
	{
		super(product + " - Ratings");
		
		name = product;
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
		modelRatingsTable = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		ratingsTable = new JTable(modelRatingsTable);
		rightClick = new JPopupMenu();
		delete = new JMenuItem(PLACEHOLDER.DELETE.toString());
		scrollRatingsTable = new JScrollPane(ratingsTable);
	}
	
	private void initialize()
	{
		add(scrollRatingsTable);
		
		scrollRatingsTable.setBounds(0, 0, this.getWidth(), this.getHeight());
	}
	
	private void generateTable()
	{
		modelRatingsTable.addColumn(PLACEHOLDER.BUYER.toString());
		modelRatingsTable.addColumn(PLACEHOLDER.RATING.toString());
		modelRatingsTable.addColumn(PLACEHOLDER.COMMENT.toString());
		modelRatingsTable.addColumn(PLACEHOLDER.DATERATING.toString());
		
		ratingsTable.getTableHeader().setResizingAllowed(false);
		ratingsTable.getTableHeader().setReorderingAllowed(false);
		ratingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		ratingsTable.setOpaque(false);
		((DefaultTableCellRenderer)ratingsTable.getDefaultRenderer(Object.class)).setOpaque(false);
		
		scrollRatingsTable.setOpaque(false);
		scrollRatingsTable.getViewport().setOpaque(false);
	}
	
	public void addController(RatingsController controller) {this.controller = controller;}
	
	private void addListener()
	{
		ratingsTable.addMouseListener(new rightClickListener());
		delete.addActionListener(new deleteListener());
		addWindowListener(new disposeListener());
	}
	
	class disposeListener extends WindowAdapter{
		@Override
		public void windowClosed(WindowEvent e) {
			controller.close();
		}
	}
	
	class deleteListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.deleteRating();
			
		}
		
	}
	
	class rightClickListener extends MouseAdapter{
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if(SwingUtilities.isRightMouseButton(e) && !ratingsTable.getSelectionModel().isSelectionEmpty())
			{
				int x = e.getX();
				int y = e.getY();
			
				controller.checkItems();
				rightClick.show(ratingsTable, x, y);
			}
		}
	}

	public JTable getRatingsTable() {
		return ratingsTable;
	}

	public void setRatingsTable(JTable ratingsTable) {
		this.ratingsTable = ratingsTable;
	}

	public DefaultTableModel getModelRatingsTable() {
		return modelRatingsTable;
	}

	public void setModelRatingsTable(DefaultTableModel modelRatingsTable) {
		this.modelRatingsTable = modelRatingsTable;
	}

	public JScrollPane getScrollRatingsTable() {
		return scrollRatingsTable;
	}

	public void setScrollRatingsTable(JScrollPane scrollRatingsTable) {
		this.scrollRatingsTable = scrollRatingsTable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	
}
