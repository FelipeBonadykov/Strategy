package Progress;

import static Constants.Constants.PROGRESS;

import java.io.File;

public final class DoNotSaveProgress 
{
	public DoNotSaveProgress() {
		try {
			File progress = new File(PROGRESS.getDirection());
			progress.delete();
		} catch(Exception e) {}
		System.exit(0);
	}
}
