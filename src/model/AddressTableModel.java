package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class AddressTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private List<Address> addresses;
	
	public AddressTableModel(List<Address> addresses)
	{
		this.addresses = new ArrayList<Address>(addresses);
	}
	
	@Override
	public int getRowCount() {
		if(addresses != null)
			return addresses.size();
		else
			return 0;
	}
	
	@Override
	public int getColumnCount() {
		return 5;
	}
	
	@Override
	public Object getValueAt(int row, int col)
	{
		Address address = addresses.get(row);
		
		switch(row)
		{
		case 0: return address.getLine1();
		case 1: return address.getLine2();
		case 2: return address.getCity();
		case 3: return address.getProvince();
		case 4: return address.getZip();
		default: return "??";
		}
		
	}
	
	public Address getAddressAt(int row)
	{
		return addresses.get(row);
	}
	
	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
}