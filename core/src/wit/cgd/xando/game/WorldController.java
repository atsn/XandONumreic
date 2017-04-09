package wit.cgd.xando.game;

import wit.cgd.xando.game.Board.GameState;
import wit.cgd.xando.game.ai.FirstSpacePlayer;
import wit.cgd.xando.game.ai.ImpactSpacePlayer;
import wit.cgd.xando.game.ai.MinimaxPlayer;
import wit.cgd.xando.game.ai.RandomSpacePlayer;
import wit.cgd.xando.game.util.GamePreferences;
import wit.cgd.xando.game.util.GameStats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Game;
import wit.cgd.xando.screens.MenuScreen;

public class WorldController extends InputAdapter
{
	final float TIME_LEFT_GAME_OVER_DELAY = 0;

	final int GAME_COUNT = 100;
	public int gameCount = 0;
	public int win = 0, draw = 0, loss = 0;

	@SuppressWarnings("unused")
	private static final String TAG = WorldRenderer.class.getName();

	public float viewportWidth;
	public float viewportHeight;
	public int width, height;
	Vector3 cameraposition;
	public Board board;
	float timeLeftGameOverDelay;
	private Game game;
	boolean dragging = false;
	int dragX, dragY;
	TextureRegion dragRegion;
	int playingnumber;

	private void backToMenu()
	{
		// switch to menu screen
		game.setScreen(new MenuScreen(game));
	}

	public WorldController(Game game)
	{
		this.game = game;
		init();
	}

	private void init()
	{

		Gdx.input.setInputProcessor(this);
		board = new Board();
		if (GamePreferences.instance.firstPlayerHuman) board.firstPlayer = new HumanPlayer(board, board.odd);
		else if (GamePreferences.instance.firstPlayerSkill == 0) board.firstPlayer = new FirstSpacePlayer(board, board.odd);
		else if (GamePreferences.instance.firstPlayerSkill == 1) board.firstPlayer = new RandomSpacePlayer(board, board.odd);
		else if (GamePreferences.instance.firstPlayerSkill == 2) board.firstPlayer = new ImpactSpacePlayer(board, board.odd);
		else board.firstPlayer = new MinimaxPlayer(board, board.odd, (int) GamePreferences.instance.firstPlayerSkill - 2);
		if (GamePreferences.instance.secondPlayerHuman) board.secondPlayer = new HumanPlayer(board, board.even);
		else if (GamePreferences.instance.secondPlayerSkill == 0) board.secondPlayer = new FirstSpacePlayer(board, board.even);
		else if (GamePreferences.instance.secondPlayerSkill == 1) board.secondPlayer = new RandomSpacePlayer(board, board.even);
		else if (GamePreferences.instance.secondPlayerSkill == 1) board.secondPlayer = new ImpactSpacePlayer(board, board.even);
		else board.secondPlayer = new MinimaxPlayer(board, board.even, (int) GamePreferences.instance.secondPlayerSkill - 2);
		timeLeftGameOverDelay = 1.5f;

		board.start();
	}

	public void update(float deltaTime)
	{

		if (board.gameState != Board.GameState.PLAYING)
		{
			timeLeftGameOverDelay -= deltaTime;
			if (timeLeftGameOverDelay < 0)
			{
				if ((board.gameState == GameState.Odd_WON && board.firstPlayer.human && !board.secondPlayer.human)
						|| (board.gameState == GameState.Even_WON && !board.firstPlayer.human && board.secondPlayer.human))
				{
					GameStats.instance.win();
				}
				else if ((board.gameState == GameState.Odd_WON && !board.firstPlayer.human && board.secondPlayer.human)
						|| (board.gameState == GameState.Even_WON && board.firstPlayer.human && !board.secondPlayer.human))
				{
					GameStats.instance.lose();
				}
				else if (!board.firstPlayer.human != !board.secondPlayer.human)
				{
					GameStats.instance.draw();
				}
				GameStats.instance.save();
				backToMenu();
			}
		}
		if (board.gameState == Board.GameState.PLAYING)
		{
			board.move();
		}

	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		if (board.gameState == Board.GameState.PLAYING && board.currentPlayer.human)
		{
			
			
			// convert to cell position
			float row = (float) (4 * (height - screenY) / (height - 0.5));
			int col2 = (int) (viewportWidth * (screenX - 0.465 * width) / width) + 1;
			int col3 = (int) (viewportWidth * (screenX - 0.54 * width) / width) + 1;
			
			if (screenX >700  && screenX<746 && screenY > 413 && screenY < 460)
			{
				board.isundopressed = true;
				board.Undo();
			}
			
			if (screenX >621  && screenX<667 && screenY > 413 && screenY < 460)
			{
				board.ishintpressed = true;
				board.Dohint();
			}
			

			dragX = screenX;
			dragY = screenY;
			if (col2 == -1)
			{
				if (row > 2.7 && board.currentPlayer == board.firstPlayer)
				{
					dragging = true;
					dragRegion = Assets.instance.number9.region;
					playingnumber = 9;
					return true;
				}
				if (row > 2.2 && board.currentPlayer == board.firstPlayer)
				{
					dragging = true;
					dragRegion = Assets.instance.number3.region;
					playingnumber = 3;
					return true;
				}
				if (row > 1.5 && board.currentPlayer == board.firstPlayer)
				{
					dragging = true;
					dragRegion = Assets.instance.number1.region;
					playingnumber = 1;
					return true;
				}
				if (row > 1 && board.currentPlayer == board.firstPlayer)
				{
					dragging = true;
					dragRegion = Assets.instance.number5.region;
					playingnumber = 5;
					return true;
				}
				if (row > 0.5 && board.currentPlayer == board.firstPlayer)
				{
					dragging = true;
					dragRegion = Assets.instance.number7.region;
					playingnumber = 7;
					return true;
				}
			}

			if (col3 == 3)
			{
				if (row > 2.3 && col3 == 3 && board.currentPlayer == board.secondPlayer)
				{
					dragging = true;
					dragRegion = Assets.instance.number4.region;
					playingnumber = 4;
					return true;
				}
				if (row > 1.7 && col3 == 3 && board.currentPlayer == board.secondPlayer)
				{
					dragging = true;
					dragRegion = Assets.instance.number2.region;
					playingnumber = 2;
					return true;
				}
				if (row > 1.2 && col3 == 3 && board.currentPlayer == board.secondPlayer)
				{
					dragging = true;
					dragRegion = Assets.instance.number6.region;
					playingnumber = 6;
					return true;
				}
				if (row > 0 && col3 == 3 && board.currentPlayer == board.secondPlayer && !board.isundopressed)
				{
					dragging = true;
					dragRegion = Assets.instance.number8.region;
					playingnumber = 8;
					return true;
				}

			}

		}

		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		dragX = screenX;
		dragY = screenY;
		return true;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		if (board.ishintpressed)
		{
			board.undohint();
		}
		board.ishintpressed = false;
		board.isundopressed = false;
		if (dragging == true)
		{
			
			dragging = false;

			// convert to cell position
			int row = 4 * (height - screenY) / height;
			int col = (int) (viewportWidth * (screenX - 0.5 * width) / width) + 1;

			// if a valid board cell then place piece
			if (row >= 0 && row < 3 && col >= 0 && col < 3 && board.gameState == GameState.PLAYING)
			{
				board.move(row, col, playingnumber);
				return true;
			}

			return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		if (keycode == Keys.ESCAPE || keycode == Keys.BACK)
		{
			backToMenu();
		}
		return false;
	}

}
