package Constants;
public enum Constants {
	ICON("files/sign.png"),
	MAP("files/Screen/MapForBattle.jpg"), 
	MOVIE("files/Start/BattleMovie.gif"),
	MUSIC("files/Start/StartMusic.wav"),
	WAR("files/Screen/sound.wav"),
	PROGRESS("files/Progress/progress.fb"),
	URL("https://strategyonline.rj.r.appspot.com/strategy");
	//URL("http://localhost:8080/strategy"); // uncomment if you test locally
	
	private String direction;
	Constants(String direction)
	{
		this.direction = direction;
	}
	public String getDirection() 
	{
		return direction;
	}
}
