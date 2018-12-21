package Screen;

import static Constants.Constants.ICON;
import static Constants.Constants.MAP;
import static Constants.Constants.WAR;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import GameEngine.GameEngine;
import Progress.DoNotSaveProgress;
import Progress.RestoreProgress;
import Progress.SaveProgress;
import Result.Victory;
import Start.StartApp;

@SuppressWarnings("serial")
public class Field extends JFrame
{
	JButton button[][] = new JButton[18][18];// game field
	private JLabel turnUP;// this label in the top of the screen will show whose turn is
	private short counter=0;// avoid same actions (like take and take, put and put)
	private short labelCounter=0;// shows whose turn to play is
	private Color deft = new Color(0.4f, 0.5f, 0.4f);//default color for field
	private Color spec = new Color(0.4f, 0.4f, 0.6f);//to mark possible ways
	private short counterForEngine;
	/*
	 * PC:x48,y47  NP:x35,y34
	 * PC:1Kx1K    NP:750, 750
	 */
	//figures
	     private ImageIcon figure;// this variable will be used to move figures
          
		 private ImageIcon soldier_1  = new ImageIcon("files/Screen/"+StartApp.player1+"/soldier.png");
		 private ImageIcon tank_1     = new ImageIcon("files/Screen/"+StartApp.player1+"/tank.png");
		 private ImageIcon airplane_1 = new ImageIcon("files/Screen/"+StartApp.player1+"/airplane.png");
		 private ImageIcon rocket_1   = new ImageIcon("files/Screen/"+StartApp.player1+"/rocket.png");
		 private ImageIcon hq_1       = new ImageIcon("files/Screen/"+StartApp.player1+"/hq.png");
          
		 private ImageIcon soldier_2  = new ImageIcon("files/Screen/"+StartApp.player2+"/soldier.png");
		 private ImageIcon tank_2     = new ImageIcon("files/Screen/"+StartApp.player2+"/tank.png");
		 private ImageIcon airplane_2 = new ImageIcon("files/Screen/"+StartApp.player2+"/airplane.png");
		 private ImageIcon rocket_2   = new ImageIcon("files/Screen/"+StartApp.player2+"/rocket.png");
		 private ImageIcon hq_2       = new ImageIcon("files/Screen/"+StartApp.player2+"/hq.png");
		 
	  // squares painting
	  void paint(int i, int j)
	  {
	 	 button[i][j].setBackground(deft);
	 	 if (i % 2 == 0) 
	 	 {// M every second from 1
	 		 if (j % 2 == 0) 
	 		 {// L every second from 1
	 			 button[i][j].setOpaque(true);
	 		 } else 
	 		 {// L every second from 2
	 			 button[i][j].setOpaque(false);
	 		 }
	 	} else 
	 	{// M every second from 2
	 		if (j % 2 == 1) 
	 		{// L every second from 2
	        	button[i][j].setOpaque(true);
	        } else 
	        {// every second from 1
	        	button[i][j].setOpaque(false);
	        }	     
	 	} 
	 }
	  // Displays the flag, symbol and the anthem  of the country which won
	  void result(JButton first, JButton second) 
	  {
		  if (first.getIcon()!=hq_1 ) {
			  switch (StartApp.player2) 
			  {
			  case "USA_army":
				  new Victory("THE UNITED STATES OF AMERICA WON", "USA");
				  break;
			  case "USSR_army":
				  new Victory("СОЮЗ СОВЕТСКИХ СОЦИАЛИСТИЧЕСКИХ РЕСПУБЛИК ПОБЕДИЛ", "USSR");
				  break;
			  case "GB_army":
				  new Victory("THE GREAT BRITAIN WON", "GB");
				  break;
			}
			  dispose();
		  }
		  if (second.getIcon()!=hq_2) 
		  {
			  switch (StartApp.player1) 
			  {
			  case "USA_army":
				  new Victory("THE UNITED STATES OF AMERICA WON", "USA");
				  break;
			  case "USSR_army":
				  new Victory("СОЮЗ СОВЕТСКИХ СОЦИАЛИСТИЧЕСКИХ РЕСПУБЛИК ПОБЕДИЛ", "USSR");
				  break;
			  case "GB_army":
				  new Victory("THE GREAT BRITAIN WON", "GB");
				  break; 
			  }
			  dispose();
		}
	}
	 // Set default position
	 void setDefaultPosition(JButton button[][]) 
	 {
     // figures are represented as Images
	    // USSR
	       // Soldiers
	       for (byte k = 1; k < 16; k++) {button[4][k].setIcon(soldier_2);}
	       button[4][4].setIcon(null);
	       button[4][8].setIcon(null);
	       button[4][12].setIcon(null);
	       for (byte k = 3; k < 14; k++) { button[1][k].setIcon(soldier_2);}
	       button[1][6].setIcon(null);
	       button[1][10].setIcon(null);	   
	       // Technique
	       for (byte k = 2; k < 16; k += 4) {button[3][k].setIcon(tank_2);}
	       for (byte k = 4; k < 13; k += 4) {button[0][k].setIcon(tank_2);}
	       button[2][6].setIcon(airplane_2);
	       button[2][10].setIcon(airplane_2);
	       button[0][6].setIcon(rocket_2);
	       button[0][10].setIcon(rocket_2);	   
	       // Head Quarter
	       button[0][9].setIcon(hq_2);
	       button[0][9].setOpaque(true);
	       button[0][9].setBackground(new Color(200, 150, 150));	
	    // USA
	      // Soldiers
	      for (byte k = 1; k < 16; k++) {button[13][k].setIcon(soldier_1);}
	      button[13][4].setIcon(null);
	      button[13][8].setIcon(null);
	      button[13][12].setIcon(null);
	      for (byte k = 3; k < 14; k++) { button[16][k].setIcon(soldier_1);}
	      button[16][6].setIcon(null);
	      button[16][10].setIcon(null);
	      // Technique
	      for (byte k = 2; k < 16; k += 4) {button[14][k].setIcon(tank_1);}
	      for (byte k = 4; k < 13; k += 4) {button[17][k].setIcon(tank_1);}
	      button[15][6].setIcon(airplane_1);
	      button[15][10].setIcon(airplane_1);
	      button[17][6].setIcon(rocket_1);
	      button[17][10].setIcon(rocket_1);  
	      // Head Quarter
	      button[17][9].setIcon(hq_1);
	      button[17][9].setOpaque(true);
	      button[17][9].setBackground(new Color(200, 150, 150));
	}
	 // game with computer
	 public Field(short count) 
	 {
			counterForEngine = count;
			{// options of display
				setTitle("STRATEGY the world 🗺");
				setIconImage(new ImageIcon(ICON.getDirection()).getImage());
				setLocation(150, 10);
				setSize(1000, 1000);
				setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				setResizable(false);
			}
			{// wall paper map
				setContentPane(new JLabel(new ImageIcon(MAP.getDirection())));
				setLayout(new FlowLayout());
			}
			{// indicator of the turn. 1st turn belongs to the USA
				JLabel turnUP=new JLabel();//this label in the top of the screen will show whose turn is
				turnUP.setPreferredSize(new Dimension(990, 10));
				turnUP.setVisible(true);
				turnUP.setOpaque(true);
				turnUP.setText("<html><font size=3 color=white>the "
				               +StartApp.player1+" versus the "
				               +StartApp.player2+"</font><html>");
				turnUP.setBackground(Color.black);
				add(turnUP);
			}
			// button array and settings
		        for (int i = 0; i < button.length; i++) 
		        {
		        	for (int j = 0; j < button.length; j++) 
		        	{
		        		button[i][j] = new JButton();
		        		button[i][j].setPreferredSize(new Dimension(48, 47));
		        		button[i][j].setVisible(true);
		        		paint(i, j);
		        	{//code to move figures
		        		MouseAdapter MoveFigure = new MouseAdapter() 
		        		{
		        			@Override
		        			public void mouseClicked(MouseEvent e) 
		        			{
		       					Object src = e.getSource();
	        					JButton btn = (JButton)src;
	        					
	        					boolean not_null_or_HQ = btn.getIcon()!=null & btn.getIcon()!=hq_1 & btn.getIcon()!=hq_2;
	        					
	       						boolean is1 = btn.getIcon()==soldier_1 | btn.getIcon()==tank_1 | 
	       								btn.getIcon()==airplane_1 | btn.getIcon()==rocket_1 | btn.getIcon()==hq_1 ;
	       						
	       						boolean is2 = btn.getIcon()==soldier_2 | btn.getIcon()==tank_2 |
	       								btn.getIcon()==airplane_2 | btn.getIcon()==rocket_2 | btn.getIcon()==hq_2 ;
	       						
	       					if (labelCounter%2==0) 
	       					{//turn of United States
		        	      			{
		        	      				if((counter%2==0 & e.getButton()==MouseEvent.BUTTON1) & not_null_or_HQ) 
		        	      				{// first action. left button click. take figure, is neither air nor HQ
		    	        					if (labelCounter%2==1 & is1) {/* to avoid blue going during red's turn */}
		    	       						if (labelCounter%2==0 & is2) {/* we avoid red going during blue's turn */}
		    	       						else 
		    	       						{
		    	       						figure = (ImageIcon) btn.getIcon();// copying figure
		    	       						btn.setIcon(null);// removing text from the button
		    	       						counter++;// counter of action
		    	       						
		    	       						class limit_movings 
		    	       						{// inner class to limit figure movings
		    	       							public limit_movings() 
		    	       							{
		    	       								for(int i=0; i<18; i++)
		    	       									for(int j=0; j<18; j++)
		    	       										if (btn==button[i][j]) 
		    	       										{
		    	       											if (figure==rocket_1) 
		    	       											{// rocket. moves +
		    	       												for (int k = -4; k < 5; k++) 
		    	       												{
		    	       													if (j+k<0) {} else if (j+k>17) {} else 
																		{
																			button[i][j+k].setBackground(spec);
																			button[i][j+k].setOpaque(true);
																		}
																	}
		    	       												for (int k = -4; k < 5; k++) 
		    	       												{
		    	       													if (i+k<0) {} else if (i+k>17) {} else 
																		{
																			button[i+k][j].setBackground(spec);
																			button[i+k][j].setOpaque(true);
																		}
																	}
		    	       												button[i][j].setBackground(Color.BLACK);
		    	       												button[i][j].setOpaque(true);
		    	       											} 
		    	       											else if (figure==airplane_1) 
		    	       											{// airplane. moves *
		    	       												for (int k = -3; k < 4; k++) 
		    	       												{
		    	       													if (j+k<0) {} else if (j+k>17) {} else 
																		{
																			button[i][j+k].setBackground(spec);
																			button[i][j+k].setOpaque(true);
																		}
																	}
		    	       												for (int k = -3; k < 4; k++) 
		    	       												{
		    	       													if (i+k<0) {} else if (i+k>17) {} else 
		    	       													{
																			button[i+k][j].setBackground(spec);
																			button[i+k][j].setOpaque(true);
																		}
																	}
		    	       												for (int k = -3; k < 4; k++) 
		    	       												{
		    	       													if (i+k<0 | j+k<0 | i+k>17 | j+k>17) {} else 
		    	       													{
																			button[i+k][j+k].setBackground(spec);
																			button[i+k][j+k].setOpaque(true);
																		}
																	}
		    	       												for (int k = -3; k < 4; k++) 
		    	       												{
		    	       													if (i-k<0 | j+k<0 | i-k>17 | j+k>17) {} else 
		    	       													{
																			button[i-k][j+k].setBackground(spec);
																			button[i-k][j+k].setOpaque(true);
																		}
																	}
		    	       												button[i][j].setBackground(Color.BLACK);
		    	       												button[i][j].setOpaque(true);
		    	       											} 
		    	       											else if (figure==tank_1) 
		    	       											{// tank. moves L
		    	       												if (j-2>17 | j-2 <0) {} else 
		    	       												{
		    	       													button[i][j-2].setBackground(spec);
		    	       													button[i][j-2].setOpaque(true);
		    	       												}
		    	       												if (j+2>17 | j+2 <0) {} else 
		    	       												{
		    	       													button[i][j+2].setBackground(spec);
		    	       													button[i][j+2].setOpaque(true);
		    	       												}
		    	       												if (i+2>17 | i+2 <0) {} else 
		    	       												{
		    	       													button[i+2][j].setBackground(spec);
		    	       													button[i+2][j].setOpaque(true);
		    	       												}
		    	       												if (i-2>17 | i-2 <0) {} else 
		    	       												{
		    	       													button[i-2][j].setBackground(spec);
		    	       													button[i-2][j].setOpaque(true);
		    	       												}
		    	       												if (i+2>17 | i+2<0 | j-1>17 | j-1<0) {} else 
		    	       												{
		    	       													button[i+2][j-1].setBackground(spec);
		    	       													button[i+2][j-1].setOpaque(true);
		    	       												}
		    	       												if (i+2>17 | i+2<0 | j+1>17 | j+1<0) {} else 
		    	       												{
		    	       													button[i+2][j+1].setBackground(spec);
		    	       													button[i+2][j+1].setOpaque(true);
		    	       												}
		    	       												if (i-2>17 | i-2<0 | j-1>17 | j-1<0) {} else 
		    	       												{
		    	       													button[i-2][j-1].setBackground(spec);
		    	       													button[i-2][j-1].setOpaque(true);
		    	       												}
		    	       												if (i-2>17 | i-2<0 | j+1>17 | j+1<0) {} else 
		    	       												{
		    	       													button[i-2][j+1].setBackground(spec);
		    	       													button[i-2][j+1].setOpaque(true);
		    	       												}
		    	       												if (i+1>17 | i+1<0 | j+2>17 | j+2<0) {} else 
		    	       												{
		    	       													button[i+1][j+2].setBackground(spec);
		    	       													button[i+1][j+2].setOpaque(true);
		    	       												}
		    	       												if (i+1>17 | i+1<0 | j-2>17 | j-2<0) {} else 
		    	       												{
		    	       													button[i+1][j-2].setBackground(spec);
		    	       													button[i+1][j-2].setOpaque(true);
		    	       												}
		    	       												if (i-1>17 | i-1<0 | j+2>17 | j+2<0) {} else 
		    	       												{
		    	       													button[i-1][j+2].setBackground(spec);
		    	       													button[i-1][j+2].setOpaque(true);
		    	       												}
		    	       												if (i-1>17 | i-1<0 | j-2>17 | j-2<0) {} else 
		    	       												{
		    	       													button[i-1][j-2].setBackground(spec);
		    	       													button[i-1][j-2].setOpaque(true);
		    	       												}
		    	       												button[i][j].setBackground(Color.BLACK);
		    	       												button[i][j].setOpaque(true);
		    	       											} 
		    	       											else if (figure==soldier_1) 
		    	       											{// soldier. moves *
		    	       												for (int k = -1; k < 2; k++) 
		    	       												{
		    	       													if (j+k<0) k++;
		    	       													else if (j+k>17){} else 
																		{
																			button[i][j+k].setBackground(spec);
																			button[i][j+k].setOpaque(true);
																		}
																	}
		    	       												for (int k = -1; k < 2; k++) 
		    	       												{
		    	       													if (i+k<0 | i+k>17) {} else 
		    	       													{
																			button[i+k][j].setBackground(spec);
																			button[i+k][j].setOpaque(true);
																		}
																	}
		    	       												for (int k = -1; k < 2; k++) {
		    	       													if (i+k<0 | j+k<0 | i+k>17 | j+k>17) {} else 
		    	       													{
																			button[i+k][j+k].setBackground(spec);
																			button[i+k][j+k].setOpaque(true);
																		}
																	}
		    	       												for (int k = -1; k < 2; k++) {
		    	       													if (i-k<0 | j+k<0 | i-k>17 | j+k>17) {} else 
		    	       													{
																			button[i-k][j+k].setBackground(spec);
																			button[i-k][j+k].setOpaque(true);
																		}
																	}
		    	       												button[i][j].setBackground(Color.BLACK);
		    	       												button[i][j].setOpaque(true);
		    	       											}
		    	       										}
		    	       							}
		    	       						}
		    	       						new limit_movings();
		    	       						}
		            					} 
		        	      				else 
		        	      				{// second action. right button click. putting figure
		    	        				if (counter%2==1 & e.getButton()==MouseEvent.BUTTON3 & 
		    	        						is1==false & btn.getBackground()==spec) {
		    	       						btn.setIcon(figure);
		    	        					counter++;
		    	        					labelCounter++;
		    	        					class paint_buttons 
		    	        					{// inner class to repaint buttons back
		    	       							public paint_buttons(JButton button[][]) 
		    	       							{// squares painting
		    	       								for(int i=0; i<18; i++)
		    	       									for(int j=0; j<18; j++) 
		    	       										paint(i, j);
		    	       							}
		    	       						}
		    	       						new paint_buttons(button);
		    		       					}
		    	       					}
		    	        			}
		    	        		} 
	       						if (labelCounter%2==1)
	       						{// turn of USSR
									new GameEngine(
											counterForEngine, 
											button, soldier_1, 
											tank_1, airplane_1, 
											rocket_1, hq_1, is2);
		    	        		    labelCounter++;
		    	        		    counterForEngine++;
		    	        		}
	       						result(button[17][9], button[0][9]);//if somebody won
		        			}
		        		};
		        		button[i][j].addMouseListener(MoveFigure);
		        	}
		        		add(button[i][j]);
		        	}
		        }
		    	setDefaultPosition(button);
		        setVisible(true);//prevents long loading
		}
		
		public Field() 
		{
			{// options of display
				setTitle("STRATEGY the world 🗺");
				setIconImage(new ImageIcon(ICON.getDirection()).getImage());
				setLocation(150, 10);
				setSize(1000, 1000);
				setResizable(false);
			}
			{// wall paper map
				setContentPane(new JLabel(new ImageIcon(MAP.getDirection() )));
				setLayout(new FlowLayout());
			}
			{// indicator of the turn. 1st turn belongs to the USA
				turnUP=new JLabel();
				turnUP.setPreferredSize(new Dimension(900, 10));
				turnUP.setVisible(true);
				turnUP.setOpaque(true);
				turnUP.setText("    Turn of the USA");
				turnUP.setBackground(Color.cyan);
				add(turnUP);
			}
			// restore the progress
			    JButton restore = new JButton();
			    restore.setForeground(Color.BLACK);
				restore.setBackground(Color.red);
				restore.setSize(35,10);
				add(restore);
			// button array and settings	
		        for (byte i = 0; i < button.length; i++) 
		        {
		        	for (byte j = 0; j < button.length; j++) 
		        	{
		        		button[i][j] = new JButton();
		        		button[i][j].setPreferredSize(new Dimension(48, 47));
		        		button[i][j].setBackground(new Color(0.4f, 0.5f, 0.4f));
		        		button[i][j].setVisible(true);
		        		paint(i, j);
		        	{//code to move figures
		        		MouseAdapter MoveFigure = new MouseAdapter() 
		        		{
		        			@Override
		        			public void mouseClicked(MouseEvent e) 
		        			{
		       					Object src = e.getSource();
	        					JButton btn = (JButton)src;
	        					//source of button
	       						boolean notNullorHQ = btn.getIcon()!=null & btn.getIcon()!=hq_1 & btn.getIcon()!=hq_2;
	       						
	       						boolean is1 = btn.getIcon()==soldier_1 | btn.getIcon()==tank_1 | 
	       								btn.getIcon()==airplane_1 | btn.getIcon()==rocket_1 | btn.getIcon()==hq_1;
	       						
	       						boolean is2 = btn.getIcon()==soldier_2 | btn.getIcon()==tank_2 |
	       								btn.getIcon()==airplane_2 | btn.getIcon()==rocket_2 | btn.getIcon()==hq_2;
	       						
	       					{
		        				if((counter%2==0 & e.getButton()==MouseEvent.BUTTON1) & notNullorHQ) 
		        				{// first action. left button click. take figure, its neither null nor HQ
		        					if (labelCounter%2==1 & is1) {/* to avoid blue going during red's turn */} 
		        					else 
		        					{
		        						if (labelCounter%2==0 & is2) {/* we avoid red going during blue's turn */}
		        						else 
		        						{
		        							figure = (ImageIcon) btn.getIcon();// copying text to the string
		        							btn.setIcon(null);// removing text from the button
		        							counter++;// counter of action
		        						}
		       						}
	        					} 
		        				else 
	        					{
		        				if (counter%2==1 & e.getButton()==MouseEvent.BUTTON3) 
		        				{// second action. right button click. putting figure
		        					if (labelCounter%2==0 & is1) {/* we avoid blue fighting blue */}
		       						else 
		       						{
		       						if (labelCounter%2==1 & is2) {/* we avoid red fighting red */}
		       						else 
		       						{// if everything goes okay
		       							if (is1 | is2) 
		       							{
		       								try 
		       								{
		       									Clip music = AudioSystem.getClip();
		       									music.open(AudioSystem.getAudioInputStream(
		       										new File(WAR.getDirection()) ));
		       									music.start();
		       								} catch (Exception ex) {}
		       							}
		       							btn.setIcon(figure);//pasting text from the string
		       							counter++;//counter of action
		       							labelCounter++;//counter of turns
		       						}
		        					}
		        				}
		       					}
	       					}
		        			{// setting indicator
		        				if (labelCounter%2==0) 
		        				{//turn of United States
		        	       			turnUP.setText("    Turn of 1st player");
		        	      			turnUP.setBackground(Color.cyan);
		        	      		} 
		        				else 
		        	      		{//turn of Soviet Union
		        	       			turnUP.setText("    Turn of 2nd player");
		        	       			turnUP.setBackground(Color.pink);
		        	      		}
		        			}
		        			result(button[17][9], button[0][9]);//if somebody won
		        			}
		        		};
		        		button[i][j].addMouseListener(MoveFigure);
		        	}
		        		add(button[i][j]);
		        	}
		        }
		    	setDefaultPosition(button);
		        addWindowListener(new WindowAdapter() 
		        {
		        	@Override
					public void windowClosing(WindowEvent e) 
		        	{
		        		String ObjButtons[] = {"Yes", "No"};
		                int PromptResult = JOptionPane.showOptionDialog
		                		(null,
		                        "Do you want to save your progress?", "SYSTEM WARNING",
		                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
		                        null,
		                        ObjButtons, ObjButtons[1]);
		                if (PromptResult == 0) 
		                    new SaveProgress
		                            (button, 
		                    		soldier_1, tank_1, airplane_1, rocket_1, hq_1, 
		                    		soldier_2, tank_2, airplane_2, rocket_2, hq_2
		                    		);
		                else new DoNotSaveProgress();
		            }
				});
		        // restore action
		    	restore.addActionListener(Restore -> new RestoreProgress
		    			(button, 
		    			soldier_1, tank_1, airplane_1, rocket_1, hq_1, 
		    			soldier_2, tank_2, airplane_2, rocket_2, hq_2
		    			));
		    	setVisible(true);//prevents long loading
		}
}