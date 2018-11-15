package controller;

import java.awt.Color;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.Card;
import model.CardsTableModel;
import model.User;
import view.CreditCards;
import view.PLACEHOLDER;

public class CreditCardsController {
	
	private CreditCards view;
	private CardsTableModel cardsModel;
	private SettingsController settings;
	private User account;
	
	public CreditCardsController(CreditCards view, User account, SettingsController settings)
	{
		this.view = view;
		this.account = account;
		this.settings = settings;
		cardsModel = null;
		view.addController(this);
		update();
	}
	
	public void delete()
	{
		Card card = cardsModel.getCardAt(view.getCardsTable().getSelectedRow());
		
		card.setUserID(account.getId());
		card.delete();
		update();
	}
	
	public void close()
	{
		view.dispose();
		settings.setCardsview(null);
	}
	
	public void update()
	{
		Card card = new Card();
		card.setUserID(account.getId());
		ArrayList<Card> cards = card.getCardsOfUser();
		
		if(cardsModel == null)
			cardsModel = new CardsTableModel(cards);
		else
			cardsModel.setCards(cards);
		
		
		for(int i = view.getModelCardsTable().getRowCount() - 1; i >= 0; i--)
			view.getModelCardsTable().removeRow(i);
		
		for(int i = 0; i < cardsModel.getRowCount(); i++)
		{
			Card c = cardsModel.getCardAt(i);
			Object[] row = new Object[] {c.getCardNumber(), c.getCvc(), c.getExpiry()};
			
			view.getModelCardsTable().addRow(row);
		}
	}
	
	public void clear()
	{
		view.getNumber().setText(PLACEHOLDER.CCNUM.toString());
		view.getCVC().setText(PLACEHOLDER.CVC.toString());
		
		view.getNumber().setForeground(Color.GRAY);
		view.getCVC().setForeground(Color.GRAY);
		
		view.getAddPanel().setVisible(false);
		
		view.getAdd().setEnabled(true);
	}
	
	public String getCardExistError()
	{
		String error = "";
		Card card = new Card();
		card.setUserID(account.getId());
		card.setCardNumber(Long.parseLong(view.getNumber().getText()));
		
		if(card.doesCardExist())
			error += "Credit Card already exist for this account.\n";
			
		return error;
	}
	
	public boolean doesCardExist()
	{
		return !getCardExistError().isEmpty();
	}
	
	public void saveCard()
	{
		Card card = new Card();
		card.setUserID(account.getId());
		card.setCardNumber(Long.parseLong(view.getNumber().getText()));
		
		if(!(view.getCVC().getText().equals(PLACEHOLDER.CVC.toString()) ||
				view.getCVC().getText().trim().isEmpty()))
			card.setCvc(true);
		else
			card.setCvc(false);		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		try {

			Date date = new Date(sdf.parse(view.getYear().getItemAt(view.getYear().getSelectedIndex()) +"-" + ((Integer)view.getMonth().getSelectedItem())).getTime());
			
			
			card.setExpiry(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		card.saveCard();
		update();
	}
	
	public boolean validateCard()
	{
		return getCardInputErrors().isEmpty();
	}
	
	public String getCardInputErrors()
	{
		String error = "";
		
		try {
			if(view.getNumber().getText().length() != 16 ||
				view.getNumber().getText().equals(PLACEHOLDER.CCNUM.toString()) ||
						view.getNumber().getText().trim().isEmpty())
				throw new NumberFormatException();
			
			Long.parseLong(view.getNumber().getText());
		}catch(Exception e)
		{
			error += "Please enter a valid credit card number. A valid number has 16 digits\n";
		}
		
		try {
			if(!(view.getCVC().getText().equals(PLACEHOLDER.CVC.toString()) ||
				view.getCVC().getText().trim().isEmpty()))
					Long.parseLong(view.getCVC().getText());
			else if(view.getCVC().getText().length() != 3)
					throw new NumberFormatException();
		}catch(Exception e)
		{
			error += "Please enter a valid CVC number, if you want to save your CVC. A valid CVC number contains 3 digits.\n";
		}
		
		return error;
	}
}
