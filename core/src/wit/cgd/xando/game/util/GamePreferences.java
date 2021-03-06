package wit.cgd.xando.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;

public class GamePreferences
{

	public static final String TAG = GamePreferences.class.getName();

	public static final GamePreferences instance = new GamePreferences();
	private Preferences prefs;

	public boolean firstPlayerHuman;
	public float firstPlayerSkill;
	public boolean secondPlayerHuman;
	public float secondPlayerSkill;
	public boolean playsound;
	public boolean playmusic;
	public float soundVolume;
	public float musicVolume;

	private GamePreferences()
	{
		prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
	}

	public void load()
	{
		firstPlayerHuman = prefs.getBoolean("firstPlayerHuman");
		secondPlayerHuman = prefs.getBoolean("secondPlayerHuman");
		firstPlayerSkill = MathUtils.clamp(prefs.getFloat("firstPlayerSkill"), 0f, 10f);
		secondPlayerSkill = MathUtils.clamp(prefs.getFloat("secondPlayerSkill"), 0f, 10f);
		soundVolume = MathUtils.clamp(prefs.getFloat("soundlevel"), 0f, 1f);
		musicVolume = MathUtils.clamp(prefs.getFloat("musiclevel"), 0f, 1f);
		playsound = prefs.getBoolean("playsound");
		playmusic = prefs.getBoolean("playmusic");
		

	}

	public void save()
	{
		prefs.putBoolean("firstPlayerHuman", firstPlayerHuman);
		prefs.putBoolean("secondPlayerHuman", secondPlayerHuman);
		prefs.putFloat("firstPlayerSkill", MathUtils.clamp(firstPlayerSkill,0f,10f));
		prefs.putFloat("secondPlayerSkill", MathUtils.clamp(secondPlayerSkill,0f,10f));
		prefs.putBoolean("playsound", playsound);
		prefs.putBoolean("playmusic", playmusic);
		prefs.putFloat("soundlevel", MathUtils.clamp(soundVolume,0f,1f));
		prefs.putFloat("musiclevel", MathUtils.clamp(musicVolume,0f,1f));
		prefs.flush();
	}

}
