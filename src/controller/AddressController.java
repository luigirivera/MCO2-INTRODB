package controller;

import java.awt.Color;
import java.util.ArrayList;

import model.Address;
import model.AddressTableModel;
import model.User;
import view.Addresses;
import view.PLACEHOLDER;

public class AddressController {
	
	private Addresses view;
	private AddressTableModel addressTableModel;
	private SettingsController settings;
	private User account;

	public AddressController(Addresses view, User account, SettingsController settings)
	{
		this.view = view;
		this.account = account;
		this.settings = settings;
		addressTableModel = null;
		view.addController(this);
		update();
	}
	
	public void delete()
	{
		Address address = addressTableModel.getAddressAt(view.getAddressTable().getSelectedRow());
		
		address.setUserID(account.getId());
		address.delete();
		update();
	}
	
	public boolean doesAddressExist() {
		return !getAddressExistError().isEmpty();
	}
	
	public void saveAddress()
	{
		Address address = new Address();
		
		address.setUserID(account.getId());
		address.setLine1(view.getLine1().getText());
		if(view.getLine2().getText().trim().isEmpty() || view.getLine2().getText().equals(PLACEHOLDER.LINE2.toString()))
			address.setLine2(null);
		else
			address.setLine2(view.getLine2().getText());
		address.setCity(view.getCity().getText());
		address.setProvince(view.getProvince().getText());
		address.setZip(Integer.parseInt(view.getZip().getText()));
		
		address.saveAddress();
		update();
	}
	
	public String getAddressExistError() {
		String error = "";
		
		Address address = new Address();
		
		address.setUserID(account.getId());
		address.setLine1(view.getLine1().getText());
		if(view.getLine2().getText().trim().isEmpty() || view.getLine2().getText().equals(PLACEHOLDER.LINE2.toString()))
			address.setLine2(null);
		else
			address.setLine2(view.getLine2().getText());
		address.setCity(view.getCity().getText());
		address.setProvince(view.getProvince().getText());
		try {
			address.setZip(Integer.parseInt(view.getZip().getText()));
			
			if(address.doesAddressExist())
				error += "Address already exist for this account.\n";
		}catch(Exception e) {}

		return error;
	}
	
	public boolean validateAddress()
	{
		return getAddressInputErrors().isEmpty();
	}
	
	public String getAddressInputErrors()
	{
		String error = "";
		
		if(view.getLine1().getText().trim().isEmpty() || view.getLine1().getText().equals(PLACEHOLDER.LINE1.toString()))
			error += "Please enter an address at line 1.\n";
		
		if(view.getCity().getText().trim().isEmpty() || view.getCity().getText().equals(PLACEHOLDER.CITY.toString()))
			error += "Please enter a city.\n";
		
		if(view.getProvince().getText().trim().isEmpty() || view.getProvince().getText().equals(PLACEHOLDER.PROV.toString()))
			error += "Please enter a province.\n";
		try {
			if(view.getZip().getText().length() != 4 || view.getZip().getText().trim().isEmpty() || view.getZip().getText().equals(PLACEHOLDER.ZIP.toString()))
				throw new NumberFormatException();
			else
				Integer.parseInt(view.getZip().getText());
		}catch(Exception e)
		{
			error += "Please enter a valid zip code. A valid zip code contains 4 digits.\n";
		}
		
		return error;
	}
	
	public void update()
	{
		Address address = new Address();
		address.setUserID(account.getId());
		ArrayList<Address> addresses = address.getAddressesOfUser();
		
		if(addressTableModel == null)
			addressTableModel = new AddressTableModel(addresses);
		else
			addressTableModel.setAddresses(addresses);
		
		
		for(int i = view.getModelAddressTable().getRowCount() - 1; i >= 0; i--)
			view.getModelAddressTable().removeRow(i);
		
		for(int i = 0; i < addressTableModel.getRowCount(); i++)
		{
			Address a = addressTableModel.getAddressAt(i);
			Object[] row = new Object[] {a.getLine1(), a.getLine2(), a.getCity(), a.getProvince(), a.getZip()};
			
			view.getModelAddressTable().addRow(row);
		}
	}
	
	public void close()
	{
		view.dispose();
		settings.setAddressview(null);
	}
	
	public void clear()
	{
		Color fg = Color.GRAY;
		view.getLine1().setForeground(fg);
		view.getLine2().setForeground(fg);
		view.getCity().setForeground(fg);
		view.getProvince().setForeground(fg);
		view.getZip().setForeground(fg);
		
		view.getLine1().setText(PLACEHOLDER.LINE1.toString());
		view.getLine2().setText(PLACEHOLDER.LINE2.toString());
		view.getCity().setText(PLACEHOLDER.CITY.toString());
		view.getProvince().setText(PLACEHOLDER.PROV.toString());
		view.getZip().setText(PLACEHOLDER.ZIP.toString());
		
		view.getAddPanel().setVisible(false);
		
		view.getAdd().setEnabled(true);
		
	}
}
