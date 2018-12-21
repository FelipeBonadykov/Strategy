package Start;

import static Constants.Constants.ICON;
import static Constants.Constants.MOVIE;
import static Constants.Constants.MUSIC;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.io.File;
import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import Screen.Field;

@SuppressWarnings("serial")
public final class StartApp extends JFrame 
{
	public static String player1;//who is the first player
	public static String player2;//who is the second player
	
	private Clip music;
	
	@SuppressWarnings("rawtypes")
	public StartApp() 
	{
		GridBagConstraints c = new GridBagConstraints();
		{// options of frame
			setTitle("STRATEGY");
			setSize(600, 339);
			setLocationRelativeTo(null);
			setResizable(false);
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			setIconImage(new ImageIcon(ICON.getDirection()).getImage());
			setContentPane(new JLabel(new ImageIcon(MOVIE.getDirection())));// picture for start background
			setLayout(new FlowLayout());
		}	 
		{// start button for player v. player
			JButton start = new JButton("TWO PLAYERS");
			start.setForeground(Color.BLACK);
			start.setBackground(Color.red);
			start.addActionListener(Start -> {
				if (player1!=null & player2!=null & player1.equals(player2)==false) 
				{
					dispose();// closing start
					music.stop();
					new Field();
				}
			});
			add(start, c);
		}
		{// start button for player v. computer
			JButton start = new JButton("SINGLE GAME");
			start.setForeground(Color.BLACK);
			start.setBackground(Color.red);
			start.addActionListener(Start -> {
				if (player1!=null & player2!=null & player1.equals(player2)==false) 
				{
					dispose();// closing start
					music.stop();
					new Field((short)0);
				}
			});
			add(start, c);                       
		}
		// preferences
		{
			final String [] items={"USSR","USA", "GB" };
			{//first player
				JComboBox<String> firstPlayer = new JComboBox<>(items);
				firstPlayer.setSelectedItem(null);
				firstPlayer.addItemListener(l-> 
				    {
				    	JComboBox<?> box = (JComboBox) l.getSource();
				    	player1 = (String)box.getSelectedItem()+"_army";
					});
				add(firstPlayer);	
			}
			{//second player
				JComboBox<String> secondPlayer = new JComboBox<>(items);
				secondPlayer.setSelectedItem(null);
				secondPlayer.addItemListener(l-> {
					JComboBox<?> box = (JComboBox) l.getSource();
					player2 = (String)box.getSelectedItem()+"_army";
				});
				add(secondPlayer);				
			}
		}		
		{// start music
			try {
				music = AudioSystem.getClip();
				music.open(AudioSystem.getAudioInputStream(new File(MUSIC.getDirection())));
				music.start();
			} catch (Exception e) {}
		}
		{// timer to show agreement right after the video is played
			 new Timer().schedule(new TimerTask() {
				 @Override
				public void run() { new Instructions(); }
		     }, Duration.ofSeconds(9).toMillis());
		}
		setVisible(true);//prevents loading
	}
}