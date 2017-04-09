package wit.cgd.xando;

import wit.cgd.xando.game.Assets;
import wit.cgd.xando.game.util.AudioManager;
import wit.cgd.xando.game.util.GamePreferences;
import wit.cgd.xando.screens.MenuScreen;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

public class XandOMain extends Game
{

	@Override
	public void create()
	{
		// Set Libgdx log level
		Gdx.app.setLogLevel(Application.LOG_NONE);
		// Load assets
		Assets.instance.init(new AssetManager());
		// Start game at menu screen
		setScreen(new MenuScreen(this));
		GamePreferences.instance.load();
		AudioManager.instance.play(Assets.instance.music.song01);
	}

}