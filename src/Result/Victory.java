package Result;

import static Constants.Constants.ICON;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public final class Victory extends JFrame
{
	public Victory(String title, String country)
	{
		{// options of frame
			setTitle(title);
		    setSize(600, 339);
		    setLocationRelativeTo(null);
		    setIconImage(new ImageIcon(ICON.getDirection()).getImage());
		    setResizable(false);
		    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    }
	    {// Anthem and Flag
	    	setContentPane(new JLabel(new ImageIcon("files/Result/"+country+"/flag.png")));
	    	try {
	    		Clip music = AudioSystem.getClip();
	    		music.open(AudioSystem.getAudioInputStream(new File("files/Result/"+country+"/himn.wav")));
	    		music.start();
	    	} catch (Exception e) {}
	    }
	    setVisible(true);//prevents loading
	 }
}