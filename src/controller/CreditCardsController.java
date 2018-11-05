package controller;

import java.awt.Color;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import driver.StopNShop;
import model.Card;
import model.CardsTableModel;
import model.User;
import view.CreditCards;
import view.PLACEHOLDER;

public class CreditCardsController {
	
	private CreditCards view;
	private CardsTableModel cardsModel;
	private StopNShop program;
	private SettingsController settings;
	private User account;
	
	public CreditCardsController(CreditCards view, StopNShop program, User account, SettingsController settings)
	{
		this.view = view;
		this.program = program;
		this.account = account;
		this.settings = settings;
		cardsModel = null;
		view.addController(this);
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
	
	public boolean doesCardExist()
	{
		Card card = new Card();
		card.setUserID(account.getId());
		card.setCardNumber(Long.parseLong(view.getNumber().getText()));
		
		return card.doesCardExist();
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
		
//		Calendar cal = Calendar.getInstance();
//		cal.set(view.getYear().getItemAt(view.getYear().getSelectedIndex()), view.getMonth().getSelectedIndex(), 1,0,0,0);
//		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Date date = new Date(sdf.parse(view.getYear().getItemAt(view.getYear().getSelectedIndex()) +"/" +
														((view.getMonth().getSelectedIndex())+1) + "/01").getTime());
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
		}catch(Exception e)
		{
			error += "Please enter a valid CVC number, if you want to save your CVC\n";
		}
		
		return error;
	}
}
