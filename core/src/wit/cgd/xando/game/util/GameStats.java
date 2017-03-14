package wit.cgd.xando.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameStats
{

	public static final String TAG = GameStats.class.getName();

	public static final GameStats instance = new GameStats();
	private Preferences prefs;
	public int gameCount;
	public int currentStreak;
	public int longestStreak;
	private GameStats()
	{
		prefs = Gdx.app.getPreferences(Constants.STATS);
	}

	public void load()
	{
		gameCount = prefs.getInteger("gameCount", 0);
		currentStreak = prefs.getInteger("currentStreak", 0);
		longestStreak = prefs.getInteger("longestStreak", 0);
	}

	public void save()
	{
		prefs.putInteger("gameCount", gameCount);
		prefs.putInteger("currentStreak", currentStreak);
		prefs.putInteger("longestStreak", longestStreak);
		prefs.flush();
	}

	public void win()
	{
		gameCount++;
		currentStreak++;
		if (currentStreak > longestStreak)
		{
			longestStreak = currentStreak;
		}
	}

	public void lose()
	{
		gameCount++;
		currentStreak = 0;
	}

	public void draw()
	{
		gameCount++;
	}

	public void reset()
	{
		gameCount = 0;
		currentStreak = 0;
		longestStreak = 0;
	}
}