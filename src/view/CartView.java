package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.CartController;

public class CartView extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JButton checkout;
	private JPopupMenu rightClick;
	private JMenuItem delete, editquantity;
	private JTable cartTable;
	private DefaultTableModel modelCartTable;
	private JScrollPane scrollCartTable;
	private CartController controller;
	private JComboBox<String> addresses, cards, accounts;
	private JRadioButton bank, card;
	private ButtonGroup group;
	private JLabel subtotal;
	
	public CartView()
	{
		super("Cart");
		
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
	
	private void instantiate()
	{
		
		checkout = new JButton("Check Out");
		
		rightClick = new JPopupMenu();
		
		delete = new JMenuItem(PLACEHOLDER.DELETE.toString());
		editquantity = new JMenuItem("Edit");
		
		modelCartTable = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		cartTable = new JTable(modelCartTable);
		
		scrollCartTable = new JScrollPane(cartTable);
		
		addresses = new JComboBox<String>();
		cards = new JComboBox<String>();
		accounts = new JComboBox<String>();
		
		bank = new JRadioButton("Bank");
		card = new JRadioButton("Card");
		
		group = new ButtonGroup();
		
		subtotal = new JLabel();
	}
	
	private void initialize()
	{
		add(checkout);
		add(scrollCartTable);
		add(subtotal);
		
		group.add(bank);
		group.add(card);
		
		rightClick.add(delete);
		rightClick.add(editquantity);
		
		subtotal.setFont(new Font("Arial", Font.BOLD, 25));
		
		subtotal.setSize(250, 40);
		subtotal.setLocation(10, 0);
		
		checkout.setSize(100, 40);
		checkout.setLocation(this.getWidth() - checkout.getWidth() - 10, 0);
		
		scrollCartTable.setSize(this.getWidth(), this.getHeight() - checkout.getHeight());
		scrollCartTable.setLocation(0, checkout.getY() + checkout.getHeight());
	}
	
	private void generateTable()
	{
		modelCartTable.addColumn(PLACEHOLDER.NAME.toString());
		modelCartTable.addColumn(PLACEHOLDER.QUANTITY.toString());
		modelCartTable.addColumn(PLACEHOLDER.DISCOUNT.toString());
		modelCartTable.addColumn(PLACEHOLDER.PRICE.toString());
		modelCartTable.addColumn(PLACEHOLDER.SHIPPING.toString());
		modelCartTable.addColumn(PLACEHOLDER.TOTAL.toString());
		
		cartTable.getTableHeader().setResizingAllowed(false);
		cartTable.getTableHeader().setReorderingAllowed(false);
		cartTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		cartTable.setOpaque(false);
		((DefaultTableCellRenderer)cartTable.getDefaultRenderer(Object.class)).setOpaque(false);
		
		scrollCartTable.setOpaque(false);
		scrollCartTable.getViewport().setOpaque(false);
	}
	
	public void addController(CartController controller) {this.controller = controller;}
	
	private void addListeners()
	{
		addWindowListener(new disposeListener());
		delete.addActionListener(new deleteListener());
		cartTable.addMouseListener(new rightClickListener());
		checkout.addActionListener(new checkoutListener());
		editquantity.addActionListener(new editListener());
	}
	
	public int toggleStockError(int quantity, int stock, String name)
	{
		return JOptionPane.showConfirmDialog(null, "You have " + name + " with " + quantity + " quantity in your cart but only " + stock + " are available.\nSelect yes if you want to adjust your cart quantity to the maximum available\nSelect no to skip the item.", "Cart Error", JOptionPane.YES_NO_OPTION);
	}
	
	public String getCVC()
	{
		JPanel panel = new JPanel();
		JTextField CVC = new JTextField(PLACEHOLDER.CVC.toString());
		CVC.setForeground(Color.GRAY);
		CVC.addFocusListener(new FocusAdapter() {
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
		});
		
		panel.add(CVC);
		panel.setPreferredSize(new Dimension(70, 30));
		CVC.setPreferredSize(new Dimension(70,30));
		int result = JOptionPane.showConfirmDialog(null, panel, "CVC", JOptionPane.YES_NO_OPTION);
		if(result == JOptionPane.YES_OPTION)
			return String.valueOf(JOptionPane.YES_OPTION);
		else
			return String.valueOf(JOptionPane.NO_OPTION);
	}
	
	public String selectAddress()
	{
		JPanel panel = new JPanel();
		panel.add(addresses);
		
		int result = JOptionPane.showConfirmDialog(null, panel, "Select Address", JOptionPane.YES_NO_OPTION);
		
		if(result == JOptionPane.YES_OPTION)
			return (String)addresses.getSelectedItem();
		else
			return String.valueOf(result);
	}
	
	public String selectPayment()
	{
		bank.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cards.setVisible(false);
				accounts.setVisible(true);
				
			}
			
		});
		
		card.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cards.setVisible(true);
				accounts.setVisible(false);
				
			}
			
		});
		
		JPanel panel = new JPanel();
		
		panel.setLayout(null);
		panel.add(bank);
		panel.add(card);
		panel.add(accounts);
		panel.add(cards);
		bank.setSelected(true);
		panel.setPreferredSize(new Dimension(300, 30));
		bank.setBounds(5, 5, 70, 20);
		card.setBounds(bank.getX() + bank.getWidth(), 5, bank.getWidth(), bank.getHeight());
		accounts.setSize(100, card.getHeight());
		accounts.setLocation(card.getX() + card.getWidth(), card.getY());
		cards.setBounds(accounts.getBounds());
		
		int result = JOptionPane.showConfirmDialog(null, panel, "Select Payment", JOptionPane.YES_NO_OPTION);
		
		if(result == JOptionPane.YES_OPTION)
			if(bank.isSelected())
				return (String)accounts.getSelectedItem();
			else
				return (String)cards.getSelectedItem();
		
		else
			return String.valueOf(JOptionPane.NO_OPTION);
	}
	
	class editListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JPanel panel = new JPanel();
			JTextField quantity = new JTextField(PLACEHOLDER.QUANTITY.toString());
			quantity.setForeground(Color.GRAY);
			quantity.addFocusListener(new FocusAdapter() {
				public void focusGained(FocusEvent e) {
					if(quantity.getText().equals(PLACEHOLDER.QUANTITY.toString()))
					{
						quantity.setText("");
						quantity.setForeground(Color.BLACK);
					}
				}
				
				public void focusLost(FocusEvent e) {
					if(quantity.getText().isEmpty())
					{
						quantity.setText(PLACEHOLDER.QUANTITY.toString());
						quantity.setForeground(Color.GRAY);
					}			
				}
			});
			
			panel.add(quantity);
			panel.setPreferredSize(new Dimension(300, 30));
			quantity.setPreferredSize(new Dimension(300,30));
			int result = JOptionPane.showConfirmDialog(null, panel, "Edit Cart", JOptionPane.OK_CANCEL_OPTION);
			if(result == JOptionPane.OK_OPTION)
			{
				if(!quantity.getText().trim().isEmpty() && !quantity.getText().equals(PLACEHOLDER.QUANTITY.toString()))
				{
					try
					{
						if(Integer.parseInt(quantity.getText()) < 0)
							throw new NumberFormatException();
						else
							if(controller.checkQuantityError(Integer.parseInt(quantity.getText())).isEmpty())
								controller.updateCart(Integer.parseInt(quantity.getText()));
							else
								JOptionPane.showMessageDialog(null, controller.checkQuantityError(Integer.parseInt(quantity.getText())), "Error", JOptionPane.ERROR_MESSAGE);
					}catch(NumberFormatException e)
					{
						JOptionPane.showMessageDialog(null, "Please enter a quantity amount", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Please enter a quantity amount", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
	}
	
	class checkoutListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
				controller.checkout(JOptionPane.YES_OPTION, JOptionPane.NO_OPTION);
			
		}
		
	}
	
	class rightClickListener extends MouseAdapter{
		@Override
		public void mouseReleased(MouseEvent e) {
			if(SwingUtilities.isRightMouseButton(e) && !cartTable.getSelectionModel().isSelectionEmpty())
			{
				int x = e.getX();
				int y = e.getY();
			
				rightClick.show(cartTable, x, y);
			}
			
		}
	}
	
	class deleteListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.delete();
			
		}
		
	}
	
	class disposeListener extends WindowAdapter{
		@Override
		public void windowClosed(WindowEvent e) {
			controller.close();
		}
	}

	public JButton getCheckout() {
		return checkout;
	}

	public void setCheckout(JButton checkout) {
		this.checkout = checkout;
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

	public JMenuItem getEditquantity() {
		return editquantity;
	}

	public void setEditquantity(JMenuItem editquantity) {
		this.editquantity = editquantity;
	}

	public JTable getCartTable() {
		return cartTable;
	}

	public void setCartTable(JTable cartTable) {
		this.cartTable = cartTable;
	}

	public DefaultTableModel getModelCartTable() {
		return modelCartTable;
	}

	public void setModelCartTable(DefaultTableModel modelCartTable) {
		this.modelCartTable = modelCartTable;
	}

	public JScrollPane getScrollCartTable() {
		return scrollCartTable;
	}

	public void setScrollCartTable(JScrollPane scrollCartTable) {
		this.scrollCartTable = scrollCartTable;
	}

	public CartController getController() {
		return controller;
	}

	public void setController(CartController controller) {
		this.controller = controller;
	}

	public JComboBox<String> getAddresses() {
		return addresses;
	}

	public void setAddresses(JComboBox<String> addresses) {
		this.addresses = addresses;
	}

	public JComboBox<String> getCards() {
		return cards;
	}

	public void setCards(JComboBox<String> cards) {
		this.cards = cards;
	}

	public JComboBox<String> getAccounts() {
		return accounts;
	}

	public void setAccounts(JComboBox<String> accounts) {
		this.accounts = accounts;
	}

	public JRadioButton getBank() {
		return bank;
	}

	public void setBank(JRadioButton bank) {
		this.bank = bank;
	}

	public JRadioButton getCard() {
		return card;
	}

	public void setCard(JRadioButton card) {
		this.card = card;
	}

	public JLabel getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(JLabel subtotal) {
		this.subtotal = subtotal;
	}
	
}
