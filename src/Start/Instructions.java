package Start;

import static Constants.Constants.ICON;

import java.awt.Checkbox;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Web.WebConnector;

@SuppressWarnings("serial")
final class Instructions extends JFrame {
	
	private final Font FONT = new Font("Arial", Font.BOLD, 13);
	private final String AGREEMENT_AND_INSTRUCTIONS =
	"<html><font face=Monospaced size=6 color=red> PLEASE PAY ATTENTION</font><br>"//beginning
			+ "<br> AGREEMENT <br>"
			+ "© PHILIPP BONADYKOV 2017 <br>"
			+ "You are allowed to modify or distribute this game <br>"			
			+ "only with permission of the author <br>"
			+ "and on the author's terms <br>"
			+ "All rights protected by the \"Copyright Law of the United States\" <br>"
			    
			+ "<br>***<br>"
			
			+ "<br> INSTRUCTIONS <br>"//instructions
			+ "Soldiers (🕴) can move to any adjacent square <br>"//pawn
			+ "Mechanized infantry (🚙) move is shaped as an “L” and 2 squares verstically and horizontally <br>"//knight
			+ "Air forces (🛧) can move up to 3 squares in a straight line in any direction <br>"//Queen
			+ "Missile forces (🚀) move in a straight line either horizontally or vertically up to 4 squares <br>"//Rook
			+ "Head Quarter (☭ or 🦅) shouldn't move <br>"
			+ "The goal of the game is to checkmate the other HQ <br><br>"//the object of the game
			+ "In online mode you can place figures as you want <br>"
			+ "behind the middle line of the field. <br>"
			+ "After you placed, click right button to start a game <br><br>"
			+ "To take a figure click left button of your mouse<br>"
			+ "To put the figure click right button<br>"			
			+ "<br>Enjoy the game!!!"
			+ "</html>";//end
	    // action for the check box YES
		ItemListener Yes = new ItemListener() 
		{
			@Override
			public void itemStateChanged(ItemEvent e) { dispose(); }
		};
		// action for the check box NO
		ItemListener No = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) { 
				WebConnector.resetServer();
				System.exit(0); 
			}
		};

	Instructions() 
	{
		// frame settings
		setTitle("Terms of use");
		setIconImage(new ImageIcon(ICON.getDirection()).getImage());
		setSize(600, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		setResizable(false);
		
		// text settings
		JLabel text = new JLabel(AGREEMENT_AND_INSTRUCTIONS);
		text.setFont(FONT);
		text.setEnabled(true);
		add(text);
		
		// check boxes settings
		Checkbox chkbxYES = new Checkbox("I agree", false);
		chkbxYES.addItemListener(Yes);
		add(chkbxYES);
		Checkbox chkbxNO = new Checkbox("I disagree", false);
		chkbxNO.addItemListener(No);
		add(chkbxNO);
		
		setVisible(true);//prevents loading
	}

}