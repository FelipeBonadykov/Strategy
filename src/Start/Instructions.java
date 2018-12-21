package Start;

import static Constants.Constants.ICON;

import java.awt.Checkbox;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
final class Instructions extends JFrame {
	private final Font FONT = new Font("Arial", Font.BOLD, 15);
	private final String AGREEMENT_AND_INSTRUCTIONS =
	"<html><font face=Monospaced size=6 color=red> PLEASE PAY ATTENTION</font><br>"//beginning
			+ "<br> AGREEMENT <br>"
			+ "© PHILIPP BONADYKOV 2017 <br>"
			+ "You are not allowed to share, edit or sell this game"
			    + "<br> without written permission of the author <br>"
			+ "All the rights are protected by the \"Copyright Law of the United States\" <br>"
			    
			+ "<br>***<br>"
			
			+ "<br> INSTRUCTIONS <br>"//instructions
			+ "Soldiers (🕴) can move to any adjacent square <br>"//pawn
			+ "Mechanized infantry (🚙) move is shaped as an “L” <br>"//knight
			+ "Air forces (🛧) can move up to 3 squares in a straight line in any direction <br>"//Queen
			+ "Missile forces (🚀) move in a straight line either horizontally or vertically up to"
			    + "<br>4 squares <br>"//Rook
			+ "Head Quarter (☭ or 🦅) can't move <br>"
			+ "The goal of the game is to checkmate the other HQ <br>"//the object of the game
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
			public void itemStateChanged(ItemEvent e) { System.exit(0); }
		};
		// action for the button "info"
	    private ActionListener link = new ActionListener() 
	    {
	    	@Override
	    	// web site of the US government
	    	public void actionPerformed(ActionEvent e) 
	    	{
	    		try 
	    		{
					java.awt.Desktop.getDesktop().browse(new URI("https://www.copyright.gov/"));
				} catch (Exception e1) {}
	    	}
	    };
	protected Instructions() 
	{
		{// frame settings
			setTitle("Terms of use");
			setIconImage(new ImageIcon(ICON.getDirection()).getImage());
			setSize(600, 600);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setLayout(new FlowLayout());
			setResizable(false);
		}
		{// text settings
			JLabel text = new JLabel(AGREEMENT_AND_INSTRUCTIONS);
			text.setFont(FONT);
			text.setEnabled(true);
			add(text);
		}
		{// copyright info in the Internet
			JButton info= new JButton("\u2922");
			info.addActionListener(link);
			add(info);
		}
		{// check boxes settings
			Checkbox chkbxYES = new Checkbox("I agree", false);
			chkbxYES.addItemListener(Yes);
			add(chkbxYES);
			Checkbox chkbxNO = new Checkbox("I disagree", false);
			chkbxNO.addItemListener(No);
			add(chkbxNO);
		}
		setVisible(true);//prevents loading
	}
}