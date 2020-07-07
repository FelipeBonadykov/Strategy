package Screen;

import static Constants.Constants.ICON;
import static Constants.Constants.MAP;
import static Constants.Constants.URL;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;

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
public class Field extends JFrame {
	JButton button[][] = new JButton[18][18];// game field
	private JLabel turnUP;// this label in the top of the screen will show whose turn is
	private int counter = 0;// avoid same actions (like take and take, put and put)
	private int labelCounter = 0;// shows whose turn to play is
	private Color deft = new Color(0.4f, 0.5f, 0.4f);// default color for field
	private Color spec = new Color(0.4f, 0.4f, 0.6f);// to mark possible ways
	private static final int height = Toolkit.getDefaultToolkit().getScreenSize().height - 70;
	private int counterForEngine = 0;

	// figures
	private ImageIcon figure;// this variable will be used to move figures

	private ImageIcon soldier1 = getScaledImage("files/Screen/" + StartApp.player1 + "/soldier.png");
	private ImageIcon tank1 = getScaledImage("files/Screen/" + StartApp.player1 + "/tank.png");
	private ImageIcon airplane1 = getScaledImage("files/Screen/" + StartApp.player1 + "/airplane.png");
	private ImageIcon rocket1 = getScaledImage("files/Screen/" + StartApp.player1 + "/rocket.png");
	private ImageIcon hq1 = getScaledImage("files/Screen/" + StartApp.player1 + "/hq.png");

	private ImageIcon soldier2 = getScaledImage("files/Screen/" + StartApp.player2 + "/soldier.png");
	private ImageIcon tank2 = getScaledImage("files/Screen/" + StartApp.player2 + "/tank.png");
	private ImageIcon airplane2 = getScaledImage("files/Screen/" + StartApp.player2 + "/airplane.png");
	private ImageIcon rocket2 = getScaledImage("files/Screen/" + StartApp.player2 + "/rocket.png");
	private ImageIcon hq2 = getScaledImage("files/Screen/" + StartApp.player2 + "/hq.png");

	// online mode
	private static String currentMap;
	private static boolean firstOrSecond;// first is true, seconds is false
	private static boolean isThreadOn = true;
	private Thread constantUpdate;

	private ImageIcon getScaledImage(String dir) {
		return new ImageIcon(new ImageIcon(dir).getImage().getScaledInstance((height - 100) / 18, (height - 140) / 18,
				Image.SCALE_DEFAULT));
	}

	private void paintButtons(int i, int j) {
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
			sendMapToServer("none");
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

	private void setDefaultPosition(JButton button[][]) {
		for (byte i = 0; i < button.length; i++)
			for (byte j = 0; j < button.length; j++) {
				button[i][j] = new JButton();
				button[i][j].setPreferredSize(new Dimension((height - 100) / 18, (height - 140) / 18));
				button[i][j].setBackground(new Color(0.4f, 0.5f, 0.4f));
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
		button[0][9].setOpaque(true);
		button[0][9].setBackground(new Color(200, 150, 150));
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
		button[17][9].setOpaque(true);
		button[17][9].setBackground(new Color(200, 150, 150));
	}

	private String getMapFromServer() {
		String line = "";
		try {
			URL url = new URL(URL.getDirection());
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
			line = br.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}

	private void sendMapToServer(String... param) {
		currentMap = param.length == 0
				? SaveProgress.getPosition(button, soldier1, tank1, airplane1, rocket1, hq1, soldier2, tank2, airplane2,
						rocket2, hq2)
				: "none";
		try {
			URL url = new URL(URL.getDirection() +"?map=" + currentMap);
			new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setButtons(String mode) {
		// two players and pc modes
		// button array and settings
		for (byte i = 0; i < button.length; i++) {
			for (byte j = 0; j < button.length; j++) {
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
								/* to avoid blue going during red's turn */} else {
								if (labelCounter % 2 == 0 & is2) {
									/* we avoid red going during blue's turn */} else {
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
									/* we avoid blue fighting blue */} else {
									if (labelCounter % 2 == 1 & is2) {
										/* we avoid red fighting red */} else {// if everything goes okay
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
								turnUP.setText("    Turn of 1st player");
								turnUP.setBackground(Color.cyan);
							} else {// turn of Soviet Union
								turnUP.setText("    Turn of 2nd player");
								turnUP.setBackground(Color.pink);
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
		// online game code
		for (byte i = 0; i < button.length; i++) {
			for (byte j = 0; j < button.length; j++) {
				// code to move figures
				MouseAdapter MoveFigure = new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						Object src = e.getSource();
						JButton btn = (JButton) src;// source of button
						
						boolean notNullOrHQ = btn.getIcon() != null & btn.getIcon() != hq1 & btn.getIcon() != hq2;
						boolean isFirst = btn.getIcon() == soldier1 | btn.getIcon() == tank1 | btn.getIcon() == airplane1
								| btn.getIcon() == rocket1 | btn.getIcon() == hq1;
						boolean isSecond = btn.getIcon() == soldier2 | btn.getIcon() == tank2 | btn.getIcon() == airplane2
								| btn.getIcon() == rocket2 | btn.getIcon() == hq2;
						boolean isMyFigure = firstOrSecond ? isFirst : isSecond;

						if (e.getButton() == MouseEvent.BUTTON1 & counter%2 == 0 & isMyFigure & notNullOrHQ) {
							figure = (ImageIcon) btn.getIcon();// copying figure
							btn.setIcon(null);// removing figure from the button
							counter++;// counter of action
						}
						if (e.getButton() == MouseEvent.BUTTON3 & counter%2 == 1 & !isMyFigure) {
							btn.setIcon(figure);// pasting text from the string					
							counter++;// counter of action
						}
						sendMapToServer();										
					}
				};
				button[i][j].addMouseListener(MoveFigure);
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
		turnUP = new JLabel();
		turnUP.setVisible(true);
		turnUP.setOpaque(true);
		add(turnUP);

		setDefaultPosition(button);

		if (str.equals("2P")) {
			turnUP.setText("    Turn of the 1st player");
			turnUP.setPreferredSize(new Dimension(height - 70, 10));
			turnUP.setBackground(Color.cyan);

			// restore the progress
			JButton restore = new JButton();
			restore.setForeground(Color.BLACK);
			restore.setBackground(Color.red);
			restore.setSize(40, 10);
			add(restore);

			setButtons("2P");

			addWindowListener(new WindowAdapter() {
				// save option
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

			restore.addActionListener(Restore -> new RestoreProgress(button, soldier1, tank1, airplane1, rocket1, hq1,
					soldier2, tank2, airplane2, rocket2, hq2));

		} else if (str.equals("PC")) {
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

			turnUP.setPreferredSize(new Dimension(height - 30, 10));
			turnUP.setText("<html><font size=3 color=white>the " + StartApp.player1 + " versus the " + StartApp.player2
					+ "</font><html>");
			turnUP.setBackground(Color.black);
			
			setButtons("PC");
			
		} else if (str.equals("WWW")) {
			turnUP.setPreferredSize(new Dimension(height - 30, 10));
			turnUP.setText("ONLINE GAME");
			turnUP.setBackground(Color.black);

			if (getMapFromServer().equals("none")) {
				firstOrSecond = true;
				sendMapToServer();
			} else 
				firstOrSecond = false;	
			
			constantUpdate = new Thread(new Runnable() {
				@Override
				public void run() {
					while (isThreadOn) {
						honorWinner(button[17][9], button[0][9]);// if somebody won	
						if (currentMap != getMapFromServer()) {
							currentMap = getMapFromServer();
							RestoreProgress.restore(button, soldier1, tank1, airplane1, rocket1, hq1, soldier2, tank2,
								airplane2, rocket2, hq2, currentMap);
						}
					}
				}
			});
			
			setButtonsWWW();	
			
			// sets value on server to "none" and exits the game
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					sendMapToServer("none");
					System.exit(0);
				}
			});
		}
		setVisible(true);// prevents long loading
	}
}