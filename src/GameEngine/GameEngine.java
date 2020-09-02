package GameEngine;

import static Constants.Constants.WAR;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Icon;
import javax.swing.JButton;

public class GameEngine {
	
	private boolean cont = true;
	// maybe rewrite one day to a file or database
	//  coordinate table (CT)  0   1    2    3   4    5   6   7   8   9    10   11   12  13   14   15   16  17   18   19   20   21   22   23   24   25  26   27
	private byte[] Xfrom = {9 , 10 , 11 , 7 , 12 , 7 , 6 , 6 , 5 , 11 , 10 , 13 , 9 , 10 , 12 , 12 , 6 , 10 , 10 , 15 , 10 , 11 , 12 , 9  , 9  , 9 , 9  , 9 };
	private byte[] Yfrom = {4 , 4  , 1  , 1 , 1  , 4 , 2 , 4 , 4 , 4  , 2  , 4  , 5 , 0  , 3  , 2  , 3 , 6  , 7  , 7  , 8  , 7  , 6  , 9  , 13 , 7 , 12 , 14};
	private byte[] Xto   = {9 , 10 , 10 , 7 , 11 , 7 , 9 , 6 , 5 , 11 , 15 , 13 , 9 , 12 , 12 , 12 , 3 , 10 , 10 , 11 , 11 , 8  , 9  , 9  , 9  , 9 , 9  , 9 };
	private byte[] Yto   = {6 , 6  , 1  , 2 , 1  , 6 , 5 , 6 , 5 , 5  , 7  , 6  , 7 , 3  , 2  , 6  , 6 , 7  , 8  ,  7 , 8  , 9  , 9  , 13 , 16 , 12, 14 , 17};
	
	//this moves figures according to CT
	private void move( JButton button[][],int count, 
			Icon soldier1, Icon tank1, Icon airplane1, Icon rocket1, Icon hq1 ) 
	{
		try{
			if (button[Yfrom[count]][Xfrom[count]].getIcon()!=soldier1 & 
				button[Yfrom[count]][Xfrom[count]].getIcon()!=tank1 & 
				button[Yfrom[count]][Xfrom[count]].getIcon()!=airplane1 & 
				button[Yfrom[count]][Xfrom[count]].getIcon()!=rocket1 & 
				button[Yfrom[count]][Xfrom[count]].getIcon()!=null) 
			{
			button[Yto[count]][Xto[count]].setIcon(button[Yfrom[count]][Xfrom[count]].getIcon());
			button[Yfrom[count]][Xfrom[count]].setIcon(null);
			}
		} catch (Exception e) {}
	}

	public GameEngine(int count, JButton button[][], 
			          Icon soldier1, Icon tank1, Icon airplane1, Icon rocket1, Icon hq1, 
			          boolean isUSSR) 
	{  //we get buttons, conditions and counters from MainField
		//This is a special case when there is a danger for HQ
		try 
		{
			for (byte i = 0; i < 3; i++) 
			for (byte j = 7; j < 12; j++) 
				if(button[i][j].getIcon()==soldier1 | 
				   button[i][j].getIcon()==tank1 | 
				   button[i][j].getIcon()==airplane1 | 
				   button[i][j].getIcon()==rocket1 ) {
					if (button[1][j].getIcon()!=soldier1 & 
						button[1][j].getIcon()!=tank1 & 
						button[1][j].getIcon()!=airplane1 & 
						button[1][j].getIcon()!=rocket1 & 
						button[1][j].getIcon()!=null)
					{
						button[i][j].setIcon(button[1][j].getIcon());
						button[1][j].setIcon(null);
					}
					cont=false;
					break;
				} 
		//if everything is okay, go according to coordinate table
		if (cont == true) move (button, count, soldier1, tank1, airplane1, rocket1, hq1);
		else if (cont == true) move (button, ++count,  soldier1, tank1, airplane1, rocket1, hq1);
		//sound of war
			Clip music = AudioSystem.getClip();
			music.open(AudioSystem.getAudioInputStream(new File(WAR.getDirection())));
			music.start();
		} catch (Exception ex) {}
	}

}