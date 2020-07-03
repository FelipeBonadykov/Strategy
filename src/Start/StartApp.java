package Start;

import static Constants.Constants.ICON;
import static Constants.Constants.MOVIE;
import static Constants.Constants.MUSIC;

import java.awt.FlowLayout;
import java.io.File;
import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import Screen.Field;

@SuppressWarnings("serial")
public final class StartApp extends JFrame {
	public static String player1;// who is the first player
	public static String player2;// who is the second player

	private Clip music;

	@SuppressWarnings("rawtypes")
	public StartApp() {
		// options of frame
		setTitle("STRATEGY");
		setSize(600, 339);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(ICON.getDirection()).getImage());
		setContentPane(new JLabel(new ImageIcon(MOVIE.getDirection())));// picture for start background
		setLayout(new FlowLayout());

		final String[] items = { "USSR", "USA", "GB" }; // pick sides

		// first player
		JComboBox<String> firstPlayer = new JComboBox<>(items);
		firstPlayer.setSelectedItem(null);
		firstPlayer.addItemListener(l -> {
			JComboBox<?> box = (JComboBox) l.getSource();
			player1 = (String) box.getSelectedItem() + "_army";
		});
		add(firstPlayer);

		// second player
		JComboBox<String> secondPlayer = new JComboBox<>(items);
		secondPlayer.setSelectedItem(null);
		secondPlayer.addItemListener(l -> {
			JComboBox<?> box = (JComboBox) l.getSource();
			player2 = (String) box.getSelectedItem() + "_army";
		});
		add(secondPlayer);

		// choose mode
		JComboBox<String> mode = new JComboBox<>(new String[] { "TWO PLAYERS", "SINGLE GAME", "ONLINE GAME" });
		mode.setSelectedItem(null);
		mode.addItemListener(start -> {
			try {
				if (player1 != null & player2 != null & player1.equals(player2) == false) {
					dispose();// closing start
					music.stop();
					JComboBox<?> box = (JComboBox) start.getSource();
					String option = "";
					switch (box.getSelectedItem().toString()) {
					case "TWO PLAYERS":
						option = "2P";
						break;
					case "SINGLE GAME":
						option = "PC";
						break;
					case "ONLINE GAME":
						option = "WWW";
						break;
					}
					new Field(option);
				}
			} catch (Exception e) {
			}
		});
		add(mode);

		// start music
		try {
			music = AudioSystem.getClip();
			music.open(AudioSystem.getAudioInputStream(new File(MUSIC.getDirection())));
			music.start();
		} catch (Exception e) {
		}

		// timer to show agreement right after the video is played
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				new Instructions();
			}
		}, Duration.ofSeconds(9).toMillis());

		setVisible(true);// prevents loading
	}
}