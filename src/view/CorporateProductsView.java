package view;

import javax.swing.JMenuItem;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import controller.CorporateProductsController;

public class CorporateProductsView extends ProductsView {
	private static final long serialVersionUID = 1L;
	private JMenuItem delete;
	private CorporateProductsController controller;

	public CorporateProductsView()
	{
		super();
		commonInstantiate();
		instantiate();
		
		commonInitialize();
		initialize();
		generateTable();
	}
	
	private void instantiate()
	{
		delete = new JMenuItem(PLACEHOLDER.DELETE.toString());
	}
	
	private void initialize()
	{
		rightClick.add(delete);
	}
	
	private void generateTable()
	{
		modelProductsTable.addColumn(PLACEHOLDER.NAME.toString());
		modelProductsTable.addColumn(PLACEHOLDER.CATEGORY.toString());
		modelProductsTable.addColumn(PLACEHOLDER.BRAND.toString());
		modelProductsTable.addColumn(PLACEHOLDER.SELLER.toString());
		modelProductsTable.addColumn(PLACEHOLDER.DESCRIPTION.toString());
		modelProductsTable.addColumn(PLACEHOLDER.STOCK.toString());
		modelProductsTable.addColumn(PLACEHOLDER.SOLD.toString());
		modelProductsTable.addColumn(PLACEHOLDER.PRICE.toString());
		modelProductsTable.addColumn(PLACEHOLDER.DISCOUNT.toString());
		modelProductsTable.addColumn(PLACEHOLDER.SHIPPING.toString());
		
		productsTable.getTableHeader().setResizingAllowed(false);
		productsTable.getTableHeader().setReorderingAllowed(false);
		productsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		productsTable.setOpaque(false);
		((DefaultTableCellRenderer)productsTable.getDefaultRenderer(Object.class)).setOpaque(false);
	}
	
	public void addController(CorporateProductsController controller) { this.controller = controller; }
}
