package Screen;

import static Constants.Constants.ICON;
import static Constants.Constants.MAP;
import static Constants.Constants.WAR;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Icon;
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
import Web.WebConnector;

@SuppressWarnings("serial")
public class Field extends JFrame {
	private static JButton button[][] = new JButton[18][18];// game field
	private JLabel whoseTurnIsLabel;// label in the top of the screen to show whose turn is
	private static int counter = 0;// to avoid same actions (like take and take, put and put)
	private static int labelCounter = 0;// counter of turn 
	private static final Color deft = new Color(0.4f, 0.5f, 0.4f);// default color for field
	private static final Color spec = new Color(0.4f, 0.4f, 0.6f);// to mark possible ways
	private static final int height = Toolkit.getDefaultToolkit().getScreenSize().height - 70;
	private static int counterForEngine = 0;
	private static boolean shouldLimitMoves = false;

	// this variable will be used to move figures
	private static Icon figure;
	// first player figures
	private static Icon soldier1 = getScaledImage("files/Screen/" + StartApp.player1 + "/soldier.png");
	private static Icon tank1 = getScaledImage("files/Screen/" + StartApp.player1 + "/tank.png");
	private static Icon airplane1 = getScaledImage("files/Screen/" + StartApp.player1 + "/airplane.png");
	private static Icon rocket1 = getScaledImage("files/Screen/" + StartApp.player1 + "/rocket.png");
	private static Icon hq1 = getScaledImage("files/Screen/" + StartApp.player1 + "/hq.png");
	// second player figures
	private static Icon soldier2 = getScaledImage("files/Screen/" + StartApp.player2 + "/soldier.png");
	private static Icon tank2 = getScaledImage("files/Screen/" + StartApp.player2 + "/tank.png");
	private static Icon airplane2 = getScaledImage("files/Screen/" + StartApp.player2 + "/airplane.png");
	private static Icon rocket2 = getScaledImage("files/Screen/" + StartApp.player2 + "/rocket.png");
	private static Icon hq2 = getScaledImage("files/Screen/" + StartApp.player2 + "/hq.png");

	// online mode
	private static String currentMap = "none";// to avoid NullPointerException
	private static boolean firstOrSecond;// first = true, second = false
	private static boolean isThreadOn = true;
	private Thread constantUpdate;

	private static Icon getScaledImage(String dir) {
		return new ImageIcon(new ImageIcon(dir).getImage().getScaledInstance((height - 100) / 18, (height - 140) / 18,
				Image.SCALE_DEFAULT));
	}

	private static void paintButtons(int i, int j) {
		button[i][j].setBackground(deft);		
		if (i % 2 == 0) {// M every second from 1
			if (j % 2 == 0) {// L every second from 1
				button[i][j].setOpaque(true);
			} else {// L every second from 2
				button[i][j].setOpaque(false);
			}
		} else {// M every second from 2
			if (j % 2 == 1) {// L every second from 2
				button[i][j].setOpaque(true);
			} else {// every second from 1
				button[i][j].setOpaque(false);
			}
		}
	}

	private void honorWinner(JButton first, JButton second) {
		String toSwitch = "null";
		if (first.getIcon() != hq1)
			toSwitch = StartApp.player2;
		else if (second.getIcon() != hq2)
			toSwitch = StartApp.player1;
	
		if (!toSwitch.equals("null")) {
			WebConnector.resetServer();
			// kill update thread
			isThreadOn = false;
			// Displays the flag, symbol and the anthem of the country which won
			switch (toSwitch) {
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
			// close battle field
			dispose();	
		}			
	}

	private static void setDefaultPosition(JButton button[][]) {
		for (byte i = 0; i < button.length; i++)
			for (byte j = 0; j < button.length; j++) {
				button[i][j] = new JButton();
				button[i][j].setPreferredSize(new Dimension((int) ((0.85 * height) / 18), (int) ((0.80 * height) / 18)));
				button[i][j].setVisible(true);
				paintButtons(i, j);
			}
		// figures are represented as Images
		// USSR
		// Soldiers
		for (byte k = 1; k < 16; k++) {
			button[4][k].setIcon(soldier2);
		}
		button[4][4].setIcon(null);
		button[4][8].setIcon(null);
		button[4][12].setIcon(null);
		for (byte k = 3; k < 14; k++) {
			button[1][k].setIcon(soldier2);
		}
		button[1][6].setIcon(null);
		button[1][10].setIcon(null);
		// Technique
		for (byte k = 2; k < 16; k += 4) {
			button[3][k].setIcon(tank2);
		}
		for (byte k = 4; k < 13; k += 4) {
			button[0][k].setIcon(tank2);
		}
		button[2][6].setIcon(airplane2);
		button[2][10].setIcon(airplane2);
		button[0][6].setIcon(rocket2);
		button[0][10].setIcon(rocket2);
		// Head Quarter
		button[0][9].setIcon(hq2);
		// USA
		// Soldiers
		for (byte k = 1; k < 16; k++) {
			button[13][k].setIcon(soldier1);
		}
		button[13][4].setIcon(null);
		button[13][8].setIcon(null);
		button[13][12].setIcon(null);
		for (byte k = 3; k < 14; k++) {
			button[16][k].setIcon(soldier1);
		}
		button[16][6].setIcon(null);
		button[16][10].setIcon(null);
		// Technique
		for (byte k = 2; k < 16; k += 4) {
			button[14][k].setIcon(tank1);
		}
		for (byte k = 4; k < 13; k += 4) {
			button[17][k].setIcon(tank1);
		}
		button[15][6].setIcon(airplane1);
		button[15][10].setIcon(airplane1);
		button[17][6].setIcon(rocket1);
		button[17][10].setIcon(rocket1);
		// Head Quarter
		button[17][9].setIcon(hq1);
	}

	private static String getMapFromServer() {
        return (String) WebConnector.getInfoFromServer().get("map");
	}
	
	private static void sendMapToServer() {
		currentMap = SaveProgress.getPosition (button, 
			soldier1, tank1, airplane1, rocket1, hq1, 
			soldier2, tank2, airplane2,	rocket2, hq2);
		if (firstOrSecond) 
			WebConnector.sendInfoToServer("map", currentMap);			
		 else 
			// rotate the field
			WebConnector.sendInfoToServer("map", new StringBuilder(currentMap).reverse().toString());
	}

	private static boolean canGo() {
		if(!shouldLimitMoves)
			return true;
		
		String turnOf = (String) WebConnector.getInfoFromServer().get("turn");
		// first player allowed to go
		if (firstOrSecond & turnOf.equals("first")) 
			return true;
		// second player allowed to go
		if (!firstOrSecond & turnOf.equals("second"))
			return true;
		// in any other case return false
		return false;
	}
	
	private void setButtons(String mode) {
		// two players and pc modes
		// button array and settings
		for (byte i = 0; i < button.length; i++) {
			for (byte j = 0; j < button[i].length; j++) {
				// code to move figures
				MouseAdapter MoveFigure = new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						Object src = e.getSource();
						JButton btn = (JButton) src;// source of button

						boolean notNullorHQ = btn.getIcon() != null & btn.getIcon() != hq1 & btn.getIcon() != hq2;
						boolean is1 = btn.getIcon() == soldier1 | btn.getIcon() == tank1 | btn.getIcon() == airplane1
								| btn.getIcon() == rocket1 | btn.getIcon() == hq1;
						boolean is2 = btn.getIcon() == soldier2 | btn.getIcon() == tank2 | btn.getIcon() == airplane2
								| btn.getIcon() == rocket2 | btn.getIcon() == hq2;

						if ((counter % 2 == 0 & e.getButton() == MouseEvent.BUTTON1) & notNullorHQ) {
							// first action. left button click. take figure, its neither null nor HQ
							if (labelCounter % 2 == 1 & is1) {
								// to avoid blue going during red's turn 
								} else {
								if (labelCounter % 2 == 0 & is2) {
									// we avoid red going during blue's turn 
									} else {
									figure = (ImageIcon) btn.getIcon();// copying figure
									btn.setIcon(null);// removing figure from the button
									counter++;// counter of action

									if (mode.equals("PC")) {
										// inner class to limit figure movings
										class limit_movings {
											public limit_movings() {
												for (int i = 0; i < 18; i++)
													for (int j = 0; j < 18; j++)
														if (btn == button[i][j]) {
															if (figure == rocket1) {// rocket. moves +
																for (int k = -4; k < 5; k++) {
																	if (j + k < 0) {
																	} else if (j + k > 17) {
																	} else {
																		button[i][j + k].setBackground(spec);
																		button[i][j + k].setOpaque(true);
																	}
																}
																for (int k = -4; k < 5; k++) {
																	if (i + k < 0) {
																	} else if (i + k > 17) {
																	} else {
																		button[i + k][j].setBackground(spec);
																		button[i + k][j].setOpaque(true);
																	}
																}
																button[i][j].setBackground(Color.BLACK);
																button[i][j].setOpaque(true);
															} else if (figure == airplane1) {// airplane. moves
																								// *
																for (int k = -3; k < 4; k++) {
																	if (j + k < 0) {
																	} else if (j + k > 17) {
																	} else {
																		button[i][j + k].setBackground(spec);
																		button[i][j + k].setOpaque(true);
																	}
																}
																for (int k = -3; k < 4; k++) {
																	if (i + k < 0) {
																	} else if (i + k > 17) {
																	} else {
																		button[i + k][j].setBackground(spec);
																		button[i + k][j].setOpaque(true);
																	}
																}
																for (int k = -3; k < 4; k++) {
																	if (i + k < 0 | j + k < 0 | i + k > 17
																			| j + k > 17) {
																	} else {
																		button[i + k][j + k].setBackground(spec);
																		button[i + k][j + k].setOpaque(true);
																	}
																}
																for (int k = -3; k < 4; k++) {
																	if (i - k < 0 | j + k < 0 | i - k > 17
																			| j + k > 17) {
																	} else {
																		button[i - k][j + k].setBackground(spec);
																		button[i - k][j + k].setOpaque(true);
																	}
																}
																button[i][j].setBackground(Color.BLACK);
																button[i][j].setOpaque(true);
															} else if (figure == tank1) {// tank. moves L
																if (j - 2 > 17 | j - 2 < 0) {
																} else {
																	button[i][j - 2].setBackground(spec);
																	button[i][j - 2].setOpaque(true);
																}
																if (j + 2 > 17 | j + 2 < 0) {
																} else {
																	button[i][j + 2].setBackground(spec);
																	button[i][j + 2].setOpaque(true);
																}
																if (i + 2 > 17 | i + 2 < 0) {
																} else {
																	button[i + 2][j].setBackground(spec);
																	button[i + 2][j].setOpaque(true);
																}
																if (i - 2 > 17 | i - 2 < 0) {
																} else {
																	button[i - 2][j].setBackground(spec);
																	button[i - 2][j].setOpaque(true);
																}
																if (i + 2 > 17 | i + 2 < 0 | j - 1 > 17 | j - 1 < 0) {
																} else {
																	button[i + 2][j - 1].setBackground(spec);
																	button[i + 2][j - 1].setOpaque(true);
																}
																if (i + 2 > 17 | i + 2 < 0 | j + 1 > 17 | j + 1 < 0) {
																} else {
																	button[i + 2][j + 1].setBackground(spec);
																	button[i + 2][j + 1].setOpaque(true);
																}
																if (i - 2 > 17 | i - 2 < 0 | j - 1 > 17 | j - 1 < 0) {
																} else {
																	button[i - 2][j - 1].setBackground(spec);
																	button[i - 2][j - 1].setOpaque(true);
																}
																if (i - 2 > 17 | i - 2 < 0 | j + 1 > 17 | j + 1 < 0) {
																} else {
																	button[i - 2][j + 1].setBackground(spec);
																	button[i - 2][j + 1].setOpaque(true);
																}
																if (i + 1 > 17 | i + 1 < 0 | j + 2 > 17 | j + 2 < 0) {
																} else {
																	button[i + 1][j + 2].setBackground(spec);
																	button[i + 1][j + 2].setOpaque(true);
																}
																if (i + 1 > 17 | i + 1 < 0 | j - 2 > 17 | j - 2 < 0) {
																} else {
																	button[i + 1][j - 2].setBackground(spec);
																	button[i + 1][j - 2].setOpaque(true);
																}
																if (i - 1 > 17 | i - 1 < 0 | j + 2 > 17 | j + 2 < 0) {
																} else {
																	button[i - 1][j + 2].setBackground(spec);
																	button[i - 1][j + 2].setOpaque(true);
																}
																if (i - 1 > 17 | i - 1 < 0 | j - 2 > 17 | j - 2 < 0) {
																} else {
																	button[i - 1][j - 2].setBackground(spec);
																	button[i - 1][j - 2].setOpaque(true);
																}
																button[i][j].setBackground(Color.BLACK);
																button[i][j].setOpaque(true);
															} else if (figure == soldier1) {// soldier. moves *
																for (int k = -1; k < 2; k++) {
																	if (j + k < 0)
																		k++;
																	else if (j + k > 17) {
																	} else {
																		button[i][j + k].setBackground(spec);
																		button[i][j + k].setOpaque(true);
																	}
																}
																for (int k = -1; k < 2; k++) {
																	if (i + k < 0 | i + k > 17) {
																	} else {
																		button[i + k][j].setBackground(spec);
																		button[i + k][j].setOpaque(true);
																	}
																}
																for (int k = -1; k < 2; k++) {
																	if (i + k < 0 | j + k < 0 | i + k > 17
																			| j + k > 17) {
																	} else {
																		button[i + k][j + k].setBackground(spec);
																		button[i + k][j + k].setOpaque(true);
																	}
																}
																for (int k = -1; k < 2; k++) {
																	if (i - k < 0 | j + k < 0 | i - k > 17
																			| j + k > 17) {
																	} else {
																		button[i - k][j + k].setBackground(spec);
																		button[i - k][j + k].setOpaque(true);
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
							}
						} else {
							// second action. right button click. putting figure
							if (mode.equals("PC")) {
								if (counter % 2 == 1 & e.getButton() == MouseEvent.BUTTON3 & is1 == false
										& btn.getBackground() == spec) {
									btn.setIcon(figure);
									counter++;
									labelCounter++;
									class paint_buttons {// inner class to repaint buttons back
										public paint_buttons(JButton button[][]) {// squares painting
											for (int i = 0; i < 18; i++)
												for (int j = 0; j < 18; j++)
													paintButtons(i, j);
										}
									}
									new paint_buttons(button);
								}
							} else if (counter % 2 == 1 & e.getButton() == MouseEvent.BUTTON3) {
								if (labelCounter % 2 == 0 & is1) {
									// we avoid blue fighting blue 
									} else {
									if (labelCounter % 2 == 1 & is2) {
										// we avoid red fighting red
									} else {// if everything goes okay
										if (is1 | is2) {
											try {
												Clip music = AudioSystem.getClip();
												music.open(
														AudioSystem.getAudioInputStream(new File(WAR.getDirection())));
												music.start();
											} catch (Exception ex) {
											}
										}
										btn.setIcon(figure);// pasting text from the string
										counter++;// counter of action
										labelCounter++;// counter of turns
									}
								}
							}
						}
						if (mode.equals("PC")) {
							if (labelCounter % 2 == 1) {// turn of USSR
								new GameEngine(counterForEngine, button, soldier1, tank1, airplane1, rocket1, hq1, is2);
								labelCounter++;
								counterForEngine++;
							}
						} else {// 2P mode
							if (labelCounter % 2 == 0) {// turn of United States
								whoseTurnIsLabel.setText("    Turn of 1st player");
								whoseTurnIsLabel.setBackground(Color.cyan);
							} else {// turn of Soviet Union
								whoseTurnIsLabel.setText("    Turn of 2nd player");
								whoseTurnIsLabel.setBackground(Color.pink);
							}
						}
						honorWinner(button[17][9], button[0][9]);// if somebody won
					}
				};
				button[i][j].addMouseListener(MoveFigure);
				add(button[i][j]);
			}
		}
	}

	private void setButtonsWWW() {
		for (byte i = 0; i < button.length; i++) {
			for (byte j = 0; j < button[i].length; j++) {
				MouseAdapter moveFigure = new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						JButton btn = (JButton) e.getSource();// source of button
						
						boolean notNullOrHQ = btn.getIcon() != null & btn.getIcon() != hq1 & btn.getIcon() != hq2;
						boolean isFirst = btn.getIcon() == soldier1 | btn.getIcon() == tank1 | btn.getIcon() == airplane1
								| btn.getIcon() == rocket1 | btn.getIcon() == hq1;
						boolean isSecond = btn.getIcon() == soldier2 | btn.getIcon() == tank2 | btn.getIcon() == airplane2
								| btn.getIcon() == rocket2 | btn.getIcon() == hq2;
						boolean isMyFigure = firstOrSecond ? isFirst : isSecond;

						if (canGo() & e.getButton() == MouseEvent.BUTTON1 & counter%2 == 0 & isMyFigure & notNullOrHQ) {
							figure = btn.getIcon();// copying figure
							btn.setIcon(null);// removing figure from the button
							counter++;						
							if (shouldLimitMoves) {
								class limit_movings {
									public limit_movings() {
										for (int i = 0; i < 18; i++)
											for (int j = 0; j < 18; j++)
												if (btn == button[i][j]) {
													if (figure == rocket1 | figure == rocket2) {// rocket. moves +
														for (int k = -4; k < 5; k++) {
															if (j + k < 0) {
															} else if (j + k > 17) {
															} else {
																button[i][j + k].setBackground(spec);
																button[i][j + k].setOpaque(true);
															}
														}
														for (int k = -4; k < 5; k++) {
															if (i + k < 0) {
															} else if (i + k > 17) {
															} else {
																button[i + k][j].setBackground(spec);
																button[i + k][j].setOpaque(true);
															}
														}
														button[i][j].setBackground(Color.BLACK);
														button[i][j].setOpaque(true);
													}
													if (figure == airplane1 | figure == airplane2) {// airplane. moves * *
														for (int k = -3; k < 4; k++) {
															if (j + k < 0) {
															} else if (j + k > 17) {
															} else {
																button[i][j + k].setBackground(spec);
																button[i][j + k].setOpaque(true);
															}
														}
														for (int k = -3; k < 4; k++) {
															if (i + k < 0) {
															} else if (i + k > 17) {
															} else {
																button[i + k][j].setBackground(spec);
																button[i + k][j].setOpaque(true);
															}
														}
														for (int k = -3; k < 4; k++) {
															if (i + k < 0 | j + k < 0 | i + k > 17
																	| j + k > 17) {
															} else {
																button[i + k][j + k].setBackground(spec);
																button[i + k][j + k].setOpaque(true);
															}
														}
														for (int k = -3; k < 4; k++) {
															if (i - k < 0 | j + k < 0 | i - k > 17
																	| j + k > 17) {
															} else {
																button[i - k][j + k].setBackground(spec);
																button[i - k][j + k].setOpaque(true);
															}
														}
														button[i][j].setBackground(Color.BLACK);
														button[i][j].setOpaque(true);
													}
													if (figure == tank1 | figure == tank2) {// tank. moves L
														if (j - 2 > 17 | j - 2 < 0) {
														} else {
															button[i][j - 2].setBackground(spec);
															button[i][j - 2].setOpaque(true);
														}
														if (j + 2 > 17 | j + 2 < 0) {
														} else {
															button[i][j + 2].setBackground(spec);
															button[i][j + 2].setOpaque(true);
														}
														if (i + 2 > 17 | i + 2 < 0) {
														} else {
															button[i + 2][j].setBackground(spec);
															button[i + 2][j].setOpaque(true);
														}
														if (i - 2 > 17 | i - 2 < 0) {
														} else {
															button[i - 2][j].setBackground(spec);
															button[i - 2][j].setOpaque(true);
														}
														if (i + 2 > 17 | i + 2 < 0 | j - 1 > 17 | j - 1 < 0) {
														} else {
															button[i + 2][j - 1].setBackground(spec);
															button[i + 2][j - 1].setOpaque(true);
														}
														if (i + 2 > 17 | i + 2 < 0 | j + 1 > 17 | j + 1 < 0) {
														} else {
															button[i + 2][j + 1].setBackground(spec);
															button[i + 2][j + 1].setOpaque(true);
														}
														if (i - 2 > 17 | i - 2 < 0 | j - 1 > 17 | j - 1 < 0) {
														} else {
															button[i - 2][j - 1].setBackground(spec);
															button[i - 2][j - 1].setOpaque(true);
														}
														if (i - 2 > 17 | i - 2 < 0 | j + 1 > 17 | j + 1 < 0) {
														} else {
															button[i - 2][j + 1].setBackground(spec);
															button[i - 2][j + 1].setOpaque(true);
														}
														if (i + 1 > 17 | i + 1 < 0 | j + 2 > 17 | j + 2 < 0) {
														} else {
															button[i + 1][j + 2].setBackground(spec);
															button[i + 1][j + 2].setOpaque(true);
														}
														if (i + 1 > 17 | i + 1 < 0 | j - 2 > 17 | j - 2 < 0) {
														} else {
															button[i + 1][j - 2].setBackground(spec);
															button[i + 1][j - 2].setOpaque(true);
														}
														if (i - 1 > 17 | i - 1 < 0 | j + 2 > 17 | j + 2 < 0) {
														} else {
															button[i - 1][j + 2].setBackground(spec);
															button[i - 1][j + 2].setOpaque(true);
														}
														if (i - 1 > 17 | i - 1 < 0 | j - 2 > 17 | j - 2 < 0) {
														} else {
															button[i - 1][j - 2].setBackground(spec);
															button[i - 1][j - 2].setOpaque(true);
														}
														button[i][j].setBackground(Color.BLACK);
														button[i][j].setOpaque(true);
													} 
													if (figure == soldier1 | figure == soldier2) {// soldier. moves *
														for (int k = -1; k < 2; k++) {
															if (j + k < 0)
																k++;
															else if (j + k > 17) {
															} else {
																button[i][j + k].setBackground(spec);
																button[i][j + k].setOpaque(true);
															}
														}
														for (int k = -1; k < 2; k++) {
															if (i + k < 0 | i + k > 17) {
															} else {
																button[i + k][j].setBackground(spec);
																button[i + k][j].setOpaque(true);
															}
														}
														for (int k = -1; k < 2; k++) {
															if (i + k < 0 | j + k < 0 | i + k > 17
																	| j + k > 17) {
															} else {
																button[i + k][j + k].setBackground(spec);
																button[i + k][j + k].setOpaque(true);
															}
														}
														for (int k = -1; k < 2; k++) {
															if (i - k < 0 | j + k < 0 | i - k > 17
																	| j + k > 17) {
															} else {
																button[i - k][j + k].setBackground(spec);
																button[i - k][j + k].setOpaque(true);
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
					
						if (e.getButton() == MouseEvent.BUTTON1 & counter%2 == 1 & !isMyFigure) {
							counter++;							
							if (shouldLimitMoves) {
								//repaint squares
								for (int i = 0; i < 18; i++)
									for (int j = 0; j < 18; j++)
										paintButtons(i, j);
								// change value of who's turn is on server to opposite								
								if (firstOrSecond) 
									WebConnector.sendInfoToServer("turn", "second");
								else 
									WebConnector.sendInfoToServer("turn", "first");
							}
							btn.setIcon(figure);// past image													
						}
						sendMapToServer();
					}
				};
				button[i][j].addMouseListener(moveFigure);
				add(button[i][j]);
			}
		}
		// constant update of field
		constantUpdate.start();
	}

	public Field(String str) {
		// options of display
		setTitle("STRATEGY the world 🗺");
		setIconImage(new ImageIcon(ICON.getDirection()).getImage());
		setLocation(150, 10);
		setSize(height, height);
		setResizable(false);

		// wall paper map
		setContentPane(new JLabel(new ImageIcon(MAP.getDirection())));
		setLayout(new FlowLayout());

		// this label in the top of the screen will show whose turn is
		whoseTurnIsLabel = new JLabel();
		whoseTurnIsLabel.setVisible(true);
		whoseTurnIsLabel.setOpaque(true);
		add(whoseTurnIsLabel);

		setDefaultPosition(button);

		if (str.equals("2P")) {
			whoseTurnIsLabel.setPreferredSize(new Dimension((int)(0.9 * height), (int) (0.02* height)));
			whoseTurnIsLabel.setText("    Turn of the 1st player");
			whoseTurnIsLabel.setBackground(Color.cyan);

			// restore the progress
			JButton restore = new JButton();
			restore.setForeground(Color.BLACK);
			restore.setBackground(Color.red);
			restore.setSize((int)(0.2 * height), (int) (0.02* height));
			add(restore);

			setButtons("2P");

			// save progress
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					String ObjButtons[] = { "Yes", "No" };
					int PromptResult = JOptionPane.showOptionDialog(null, "Do you want to save your progress?",
							"SYSTEM WARNING", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons,
							ObjButtons[1]);
					if (PromptResult == 0)
						new SaveProgress(button, soldier1, tank1, airplane1, rocket1, hq1, soldier2, tank2, airplane2,
								rocket2, hq2);
					else
						new DoNotSaveProgress();
					System.exit(0);
				}
			});

			restore.addActionListener(restoreProgress -> new RestoreProgress(button, soldier1, tank1, airplane1, rocket1, hq1,
					soldier2, tank2, airplane2, rocket2, hq2));

		} else if (str.equals("PC")) {
			whoseTurnIsLabel.setPreferredSize(new Dimension( (int) (0.97 * height), (int) (0.02* height)));
			whoseTurnIsLabel.setText("<html><font size=3 color=white>the " + StartApp.player1 + 
					" versus the " + StartApp.player2+ "</font><html>");
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			whoseTurnIsLabel.setBackground(Color.black);
			
			setButtons("PC");
			
		} else if (str.equals("WWW")) {
			whoseTurnIsLabel.setPreferredSize(new Dimension((int)(0.9 * height), (int) (0.02* height)));
			whoseTurnIsLabel.setText("Place your figures as you want on your half of field and press the red button --->");
			whoseTurnIsLabel.setBackground(Color.LIGHT_GRAY);
			
			// limit moves after player placed figures by his wish
			JButton allowToLimitMoves = new JButton();
			allowToLimitMoves.setForeground(Color.BLACK);
			allowToLimitMoves.setBackground(Color.red);
			allowToLimitMoves.setSize((int)(0.2 * height), (int) (0.02* height));
			add(allowToLimitMoves);

			if (getMapFromServer().equals("none")) {
				firstOrSecond = true;
				sendMapToServer();
				WebConnector.sendInfoToServer("turn", "first");
			} else {
				firstOrSecond = false;
				RestoreProgress.restore(button, 
					soldier1, tank1, airplane1, rocket1, hq1,
					soldier2, tank2, airplane2, rocket2, hq2, 
					new StringBuilder(getMapFromServer()).reverse().toString());
			}	
			
			constantUpdate = new Thread(new Runnable() {
				@Override
				public void run() {
					while (isThreadOn) {
						// update label move
						if (shouldLimitMoves)
							if (canGo()) {
								whoseTurnIsLabel.setText("    Your turn");
								whoseTurnIsLabel.setBackground(Color.cyan);
							} else {
								whoseTurnIsLabel.setText("    Wait for your opponent");
								whoseTurnIsLabel.setBackground(Color.pink);
							}							
						// update battle field if there is modification on server
						var map = getMapFromServer();
						if (!currentMap.equals(map)) {
							currentMap = map;
							if (firstOrSecond) {
								RestoreProgress.restore(button, soldier1, tank1, airplane1, rocket1, hq1,
										soldier2, tank2, airplane2, rocket2, hq2, currentMap);
								honorWinner(button[17][9], button[0][9]);// if somebody won	
							} else {
								// reverse field for other players convenience
								RestoreProgress.restore(button, soldier1, tank1, airplane1, rocket1, hq1,
										soldier2, tank2, airplane2, rocket2, hq2, 
										new StringBuilder(currentMap).reverse().toString());
								honorWinner(button[0][8], button[17][8]);// if somebody won	
							}							
						}
					}
				}
			});
			
			setButtonsWWW();
			// exit the game
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					WebConnector.resetServer();
					System.exit(0);
				}
			});
			// after a player has placed all figures as wished, the moves must be limited
			allowToLimitMoves.addActionListener(limitMoves -> shouldLimitMoves = true);
		}
		setVisible(true);// prevents long loading
	}
}