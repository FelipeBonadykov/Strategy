package Progress;

import static Constants.Constants.PROGRESS;

import java.io.FileWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public final class SaveProgress 
{
	public SaveProgress (JButton button[][], 
		ImageIcon soldier1, ImageIcon tank1, ImageIcon airplane1, ImageIcon rocket1, ImageIcon hq1, 
		ImageIcon soldier2, ImageIcon tank2, ImageIcon airplane2, ImageIcon rocket2, ImageIcon hq2) 
	{
		try(FileWriter progress = new FileWriter(PROGRESS.getDirection(), false)) 
		{
			for (byte i = 0; i < 18; i++) 
			{
				for (byte j = 0; j < 18; j++) 
				{
					if (button[i][j].getIcon() == null)       {progress.write('o');} else //o=0
						
					if (button[i][j].getIcon() == soldier1)   {progress.write('s');} else //soldier
				    if (button[i][j].getIcon() == tank1)      {progress.write('t');} else //tank
				    if (button[i][j].getIcon() == airplane1)  {progress.write('a');} else //airplane
				    if (button[i][j].getIcon() == rocket1)    {progress.write('r');} else //rocket
				    if (button[i][j].getIcon() == hq1)        {progress.write('h');} else //head
				    	
				    if (button[i][j].getIcon() == soldier2)   {progress.write('S');} else //Soldier
				    if (button[i][j].getIcon() == tank2)      {progress.write('T');} else //Tank
				    if (button[i][j].getIcon() == airplane2)  {progress.write('A');} else //Airplane
			        if (button[i][j].getIcon() == rocket2)    {progress.write('R');} else //Rocket
				    if (button[i][j].getIcon() == hq2)        {progress.write('H');}      //Head
				}
			}
		}catch(Exception e){} 
		System.exit(0);
	}
}