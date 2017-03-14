package wit.cgd.xando.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;

public class GamePreferences
{

	public static final String TAG = GamePreferences.class.getName();

	public static final GamePreferences instance = new GamePreferences();
	private Preferences prefs;

	boolean firstPlayerHuman;
	float firstPlayerSkill;
	boolean secondPlayerHuman;
	float secondPlayerSkill;

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

	}

	public void save()
	{
		prefs.putBoolean("firstPlayerHuman", firstPlayerHuman);
		prefs.putBoolean("secondPlayerHuman", secondPlayerHuman);
		prefs.putFloat("firstPlayerSkill", MathUtils.clamp(firstPlayerSkill,0f,10f));
		prefs.putFloat("secondPlayerSkill", MathUtils.clamp(secondPlayerSkill,0f,10f));
		prefs.flush();
	}

}
