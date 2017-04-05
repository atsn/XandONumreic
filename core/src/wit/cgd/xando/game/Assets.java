package wit.cgd.xando.game;

import wit.cgd.xando.game.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets implements Disposable, AssetErrorListener
{

	@SuppressWarnings("unused")
	private static final String TAG = WorldRenderer.class.getName();

	public static final Assets instance = new Assets();
	private AssetManager assetManager;

	public AssetFonts fonts;

	public Asset board;
	public Asset x;
	public Asset o;
	public Asset number1;
	public Asset number2;
	public Asset number3;
	public Asset number4;
	public Asset number5;
	public Asset number6;
	public Asset number7;
	public Asset number8;
	public Asset number9;
	public AssetSounds sounds;
	public AssetMusic music;

	public void init(AssetManager assetManager)
	{
		this.assetManager = assetManager;
		// set asset manager error handler
		assetManager.setErrorListener(this);
		// load texture atlas
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
		// start loading assets and wait until finished
		assetManager.finishLoading();

		Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
		for (String a : assetManager.getAssetNames())
			Gdx.app.debug(TAG, "asset: " + a);

		TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);

		// enable texture filtering for pixel smoothing
		for (Texture t : atlas.getTextures())
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		fonts = new AssetFonts();

		// build game resource objects
		board = new Asset(atlas, "board2");
		x = new Asset(atlas, "x");
		o = new Asset(atlas, "o");
		number1 = new Asset(atlas, "1");
		number2 = new Asset(atlas, "2");
		number3 = new Asset(atlas, "3");
		number4 = new Asset(atlas, "4");
		number5 = new Asset(atlas, "5");
		number6 = new Asset(atlas, "6");
		number7 = new Asset(atlas, "7");
		number8 = new Asset(atlas, "8");
		number9 = new Asset(atlas, "9");
		
	
		// load sounds
		assetManager.load("sounds/first.wav", Sound.class);
		assetManager.load("sounds/second.wav", Sound.class);
		assetManager.load("sounds/win.wav", Sound.class);
		assetManager.load("sounds/draw.wav", Sound.class);

		// load music
		assetManager.load("music/keith303_-_brand_new_highscore.mp3", Music.class);

		assetManager.finishLoading();
	
		music = new AssetMusic(assetManager);
		sounds = new AssetSounds(assetManager);
		

	}

	@Override
	public void dispose()
	{
		assetManager.dispose();
	}

	@Override
	public void error(AssetDescriptor asset, Throwable throwable)
	{
		Gdx.app.error(TAG, "Couldn't load asset '" + asset + "'", (Exception) throwable);
	}

	public class Asset
	{
		public final AtlasRegion region;

		public Asset(TextureAtlas atlas, String imageName)
		{
			region = atlas.findRegion(imageName);
		}
	}

	public class AssetFonts
	{
		public final BitmapFont defaultSmall;
		public final BitmapFont defaultNormal;
		public final BitmapFont defaultBig;

		public AssetFonts()
		{
			// create three fonts using Libgdx's built-in 15px bitmap font
			defaultSmall = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
			defaultNormal = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
			defaultBig = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
			// set font sizes
			defaultSmall.getData().setScale(0.75f);
			defaultNormal.getData().setScale(1.0f);
			defaultBig.getData().setScale(4.0f);

			// enable linear texture filtering for smooth fonts
			defaultSmall.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultBig.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
	}

	public class AssetSounds
	{

		public final Sound first;
		public final Sound second;
		public final Sound win;
		public final Sound draw;

		public AssetSounds(AssetManager am)
		{
			first = am.get("sounds/first.wav", Sound.class);
			second = am.get("sounds/second.wav", Sound.class);
			win = am.get("sounds/win.wav", Sound.class);
			draw = am.get("sounds/draw.wav", Sound.class);
		}
	}

	public class AssetMusic
	{
		public final Music song01;

		public AssetMusic(AssetManager am)
		{
			song01 = am.get("music/keith303_-_brand_new_highscore.mp3", Music.class);
		}
	}

	private Assets()
	{}

}