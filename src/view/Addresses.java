package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.AddressController;

public class Addresses extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton add, save, cancel;
	private JPanel addPanel;
	private JTextField line1, line2, city, province, zip;
	private JTable addressTable;
	private DefaultTableModel modelAddressTable;
	private JScrollPane scrollAddressTable;
	private JPopupMenu rightClick;
	private JMenuItem delete;
	private AddressController controller;
	
	public Addresses()
	{
		super("Addresses");
		
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
	
	private void instantiate() {
		save = new JButton(PLACEHOLDER.SAVE.toString());
		add = new JButton(PLACEHOLDER.ADD.toString());
		cancel = new JButton(PLACEHOLDER.CANCEL.toString());
		
		addPanel = new JPanel(null);
		
		line1 = new JTextField(PLACEHOLDER.LINE1.toString());
		line2 = new JTextField(PLACEHOLDER.LINE2.toString());
		city = new JTextField(PLACEHOLDER.CITY.toString());
		province = new JTextField(PLACEHOLDER.PROV.toString());
		zip = new JTextField(PLACEHOLDER.ZIP.toString());
		
		modelAddressTable = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		addressTable = new JTable(modelAddressTable);
		
		scrollAddressTable = new JScrollPane(addressTable);
		
		rightClick = new JPopupMenu();
		
		delete = new JMenuItem("Delete");
		
	}
	
	private void initialize()
	{
		add(addPanel);
		addPanel.add(line1);
		addPanel.add(line2);
		addPanel.add(city);
		addPanel.add(province);
		addPanel.add(zip);
		
		addPanel.add(save);
		addPanel.add(cancel);
		add(add);
		
		addPanel.setVisible(false);
		add(scrollAddressTable);
		
		rightClick.add(delete);
		
		Color fg = Color.GRAY;
		line1.setForeground(fg);
		line2.setForeground(fg);
		city.setForeground(fg);
		province.setForeground(fg);
		zip.setForeground(fg);
		
		
		add.setSize(100, 40);
		add.setLocation(this.getWidth() - add.getWidth() - 10, 10);
		
		addPanel.setSize(this.getWidth() - add.getWidth() - 10, add.getHeight());
		addPanel.setLocation(10, 10);
		
		line1.setSize(150, addPanel.getHeight());
		line1.setLocation(0, 0);
		
		line2.setSize(line1.getSize());
		line2.setLocation(line1.getX() + line1.getWidth(), line1.getY());
	
		city.setSize(line2.getWidth()/2, line2.getHeight());
		city.setLocation(line2.getX() + line2.getWidth(), line2.getY());
		
		province.setSize(city.getSize());
		province.setLocation(city.getX() + city.getWidth(), city.getY());
		
		zip.setSize(province.getWidth()/2, province.getHeight());
		zip.setLocation(province.getX() + province.getWidth(), province.getY());
		
		save.setSize(city.getSize());
		save.setLocation(zip.getX() + zip.getWidth(), zip.getY());
		
		cancel.setSize(save.getSize());
		cancel.setLocation(save.getX() + save.getWidth(), save.getY());
	
		scrollAddressTable.setSize(this.getWidth(), this.getHeight() - addPanel.getHeight());
		scrollAddressTable.setLocation(0, addPanel.getY() + addPanel.getHeight());
	}
	
	private void generateTable()
	{
		modelAddressTable.addColumn(PLACEHOLDER.LINE1.toString());
		modelAddressTable.addColumn(PLACEHOLDER.LINE2.toString());
		modelAddressTable.addColumn(PLACEHOLDER.CITY.toString());
		modelAddressTable.addColumn(PLACEHOLDER.PROV.toString());
		modelAddressTable.addColumn(PLACEHOLDER.ZIP.toString());
		
		addressTable.getTableHeader().setResizingAllowed(false);
		addressTable.getTableHeader().setReorderingAllowed(false);
		addressTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		addressTable.setOpaque(false);
		((DefaultTableCellRenderer)addressTable.getDefaultRenderer(Object.class)).setOpaque(false);
		
		scrollAddressTable.setOpaque(false);
		scrollAddressTable.getViewport().setOpaque(false);
		
	}
	
	public void addController(AddressController controller)
	{
		this.controller = controller;
	}
	
	
	private void addListeners()
	{
		line1.addFocusListener(new line1Focus());
		line2.addFocusListener(new line2Focus());
		city.addFocusListener(new cityFocus());
		province.addFocusListener(new provinceFocus());
		zip.addFocusListener(new zipFocus());
		add.addActionListener(new addListener());
		save.addActionListener(new doneListener());
		cancel.addActionListener(new doneListener());
		addWindowListener(new disposeListener());
	}
	
	class doneListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(save))
				if(controller.validateAddress() && !controller.doesAddressExist())
				{
					controller.saveAddress();
					controller.clear();
				}
				else
					JOptionPane.showMessageDialog(null, controller.getAddressInputErrors().concat(controller.getAddressExistError()), "Error", JOptionPane.ERROR_MESSAGE);
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
	
	class line1Focus extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(line1.getText().equals(PLACEHOLDER.LINE1.toString()))
			{
				line1.setText("");
				line1.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(line1.getText().isEmpty())
			{
				line1.setText(PLACEHOLDER.LINE1.toString());
				line1.setForeground(Color.GRAY);
			}			
		}
	}
	
	class line2Focus extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(line2.getText().equals(PLACEHOLDER.LINE2.toString()))
			{
				line2.setText("");
				line2.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(line2.getText().isEmpty())
			{
				line2.setText(PLACEHOLDER.LINE2.toString());
				line2.setForeground(Color.GRAY);
			}			
		}
	}
	
	class cityFocus extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(city.getText().equals(PLACEHOLDER.CITY.toString()))
			{
				city.setText("");
				city.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(city.getText().isEmpty())
			{
				city.setText(PLACEHOLDER.CITY.toString());
				city.setForeground(Color.GRAY);
			}			
		}
	}
	
	class provinceFocus extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(province.getText().equals(PLACEHOLDER.PROV.toString()))
			{
				province.setText("");
				province.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(province.getText().isEmpty())
			{
				province.setText(PLACEHOLDER.PROV.toString());
				province.setForeground(Color.GRAY);
			}			
		}
	}
	
	class zipFocus extends FocusAdapter{
		public void focusGained(FocusEvent e) {
			if(zip.getText().equals(PLACEHOLDER.ZIP.toString()))
			{
				zip.setText("");
				zip.setForeground(Color.BLACK);
			}
		}
		
		public void focusLost(FocusEvent e) {
			if(zip.getText().isEmpty())
			{
				zip.setText(PLACEHOLDER.ZIP.toString());
				zip.setForeground(Color.GRAY);
			}			
		}
	}

	public JButton getAdd() {
		return add;
	}

	public void setAdd(JButton add) {
		this.add = add;
	}

	public JButton getSave() {
		return save;
	}

	public void setSave(JButton save) {
		this.save = save;
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

	public JTextField getLine1() {
		return line1;
	}

	public void setLine1(JTextField line1) {
		this.line1 = line1;
	}

	public JTextField getLine2() {
		return line2;
	}

	public void setLine2(JTextField line2) {
		this.line2 = line2;
	}

	public JTextField getCity() {
		return city;
	}

	public void setCity(JTextField city) {
		this.city = city;
	}

	public JTextField getProvince() {
		return province;
	}

	public void setProvince(JTextField province) {
		this.province = province;
	}

	public JTextField getZip() {
		return zip;
	}

	public void setZip(JTextField zip) {
		this.zip = zip;
	}

	public JTable getAddressTable() {
		return addressTable;
	}

	public void setAddressTable(JTable addressTable) {
		this.addressTable = addressTable;
	}

	public DefaultTableModel getModelAddressTable() {
		return modelAddressTable;
	}

	public void setModelAddressTable(DefaultTableModel modelAddressTable) {
		this.modelAddressTable = modelAddressTable;
	}

	public JScrollPane getScrollAddressTable() {
		return scrollAddressTable;
	}

	public void setScrollAddressTable(JScrollPane scrollAddressTable) {
		this.scrollAddressTable = scrollAddressTable;
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
