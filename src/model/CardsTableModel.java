package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class CardsTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private List<Card> cards;
	
	public CardsTableModel(List<Card> cards)
	{
		this.cards = new ArrayList<Card>(cards);
	}
	
	@Override
	public int getRowCount() {
		if(cards != null)
			return cards.size();
		else
			return 0;
	}
	
	@Override
	public int getColumnCount() {
		return 3;
	}
	
	@Override
	public Object getValueAt(int row, int col)
	{
		Card card = cards.get(row);
		
		switch(row)
		{
		case 0: return card.getCardNumber();
		case 1: return card.getCvc();
		case 2: return card.getExpiry();
		default: return "??";
		}
		
	}
	
	public Card getCardAt(int row)
	{
		return cards.get(row);
	}
	
	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
}
