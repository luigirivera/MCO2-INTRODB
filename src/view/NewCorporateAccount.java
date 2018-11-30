package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.NewCorpController;

public class NewCorporateAccount extends JFrame {
	private static final long serialVersionUID = 1L;
	private Signup signupPanel;
	private NewCorpController controller;

	public NewCorporateAccount()
	{
		super("New Corporate Account");
		setLayout(null);
		
		instantiate();
		initialize();
		setListeners();
		
		setSize(450,350);
		
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void instantiate()
	{
		signupPanel = new Signup();
	}
	
	private void initialize()
	{
		add(signupPanel);
		
		signupPanel.setSize(400,300);
		signupPanel.setLocation(0, 0);
	}
	
	private void setListeners()
	{
		signupPanel.getSignup().addActionListener(new signupConfirmListener());
		signupPanel.getCancel().addActionListener(new cancelListener());
	}
	
	public void addController(NewCorpController controller) {this.controller = controller;}
	
	
	class cancelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			controller.dispose();
		}
		
	}
	
	class signupConfirmListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (controller.isSignupValid())
				if(controller.isAccountFree())
				{
					controller.registerAccount();
					controller.dispose();
				}
				else

					JOptionPane.showMessageDialog(null, controller.doesUsernameExist().concat(controller.doesContactExist()), "Error", JOptionPane.ERROR_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, controller.getErrors(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	public Signup getSignupPanel() {
		return signupPanel;
	}

	public void setSignupPanel(Signup signupPanel) {
		this.signupPanel = signupPanel;
	}
	
	
}
