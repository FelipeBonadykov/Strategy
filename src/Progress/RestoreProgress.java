package Progress;

import static Constants.Constants.PROGRESS;

import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public final class RestoreProgress 
{
	public RestoreProgress(JButton button[][], 
		ImageIcon soldier1, ImageIcon tank1, ImageIcon airplane1, ImageIcon rocket1, ImageIcon hq1, 
		ImageIcon soldier2, ImageIcon tank2, ImageIcon airplane2, ImageIcon rocket2, ImageIcon hq2) 
	{
		try(FileReader progress = new FileReader(PROGRESS.getDirection())) 
		{
			ArrayList<Character> txt= new ArrayList<>();// it will contain elements from file
			short index=0;//index of array list
			int c; while((c = progress.read())!=-1) { txt.add((char)c); };//we add all the elements from the file to txt
			for (byte i = 0; i < 18; i++) 
			{
				for (byte j = 0; j < 18; j++) 
				{
					char inSwitch = txt.get (index++);//import of array element to the local char that will be used in switch
					switch (inSwitch) 
        			{
        			case 's':
						button[i][j]. setIcon (soldier1);
						break;
					case 't':
						button[i][j] . setIcon (tank1);
						break;         
					case 'a':          
						button[i][j] . setIcon (airplane1);
						break;                 
					case 'r':                  
						button[i][j] . setIcon (rocket1);
						break;                 
					case 'h':                  
						button[i][j] . setIcon (hq1);
						break;	               
						                       
					case 'S':                  
						button[i][j] . setIcon (soldier2);
						break;                   
					case 'T':                    
						button[i][j] . setIcon (tank2);
						break;                  
					case 'A':                   
						button[i][j] . setIcon (airplane2);
						break;                 
					case 'R':                  
						button[i][j] . setIcon (rocket2);
						break;                 
					case 'H':                  
						button[i][j] . setIcon (hq2);
						break;                 
							                   
					default:                   
						button[i][j] . setIcon (null);
						break;
					}
				}
			} 
		} catch(Exception e) {/*file may not exist*/}
	}
}
