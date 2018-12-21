@ProjectInformation
(
	authorsName= "Philipp Bonadykov",
	lastModified= "October 31, 2018" ,
	programVersion= 3.0,
	reviewers= {"Daniel Porokhnya", "Leo Lyapunov", "Nikolay Iwashewich", "Igor Bonadykov", "Dmitryi Fominykh"}
)
public class Main 
{
	public static void main(String... args) 
	{
		new Thread( new Runnable() 
		{
			@Override
			public void run() 
			{
				new Start.StartApp();
			}
		}).start();
	}
}