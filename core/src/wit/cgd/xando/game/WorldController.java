package wit.cgd.xando.game;

import wit.cgd.xando.game.ai.FirstSpacePlayer;
import wit.cgd.xando.game.ai.ImpactSpacePlayer;
import wit.cgd.xando.game.ai.MinimaxPlayer;
import wit.cgd.xando.game.ai.MinimaxPlayerTeacher;
import wit.cgd.xando.game.ai.RandomImpactSpacePlayer;
import wit.cgd.xando.game.ai.RandomSpacePlayer;
import wit.cgd.xando.game.util.GamePreferences;
import wit.cgd.xando.game.util.GameStats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

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
	public int width, height;
	public Board board;
	float timeLeftGameOverDelay;
	private Game game;
	boolean dragging = false;
	int dragX, dragY;
	TextureRegion dragRegion;

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
		else board.firstPlayer = new MinimaxPlayerTeacher(board, board.odd,(int) GamePreferences.instance.firstPlayerSkill);
		if (GamePreferences.instance.secondPlayerHuman) board.secondPlayer = new HumanPlayer(board, board.even);
		else board.secondPlayer = new MinimaxPlayerTeacher(board, board.even,(int) GamePreferences.instance.secondPlayerSkill);
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
				if ((board.gameState == board.gameState.Odd_WON && board.firstPlayer.human && !board.secondPlayer.human)
						|| (board.gameState == board.gameState.Even_WON && !board.firstPlayer.human && board.secondPlayer.human))
				{
					GameStats.instance.win();
				}
				else if ((board.gameState == board.gameState.Odd_WON && !board.firstPlayer.human && board.secondPlayer.human)
						|| (board.gameState == board.gameState.Even_WON && board.firstPlayer.human && !board.secondPlayer.human))
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
			int row = 4 * (height - screenY) / height;
			int col2 = (int) (viewportWidth * (screenX - 0.465 * width) / width) + 1;
			int col3 = (int) (viewportWidth * (screenX - 0.54 * width) / width) + 1;
			int col = (int) (viewportWidth * (screenX - 0.5 * width) / width) + 1;
			// board move - just place piece and return
			if (row >= 0 && row < 3 && col >= 0 && col < 3)
			{
				board.move(row, col);
				return true;
			}

			dragX = screenX;
			dragY = screenY;

			// check if valid start of a drag for first player
			if (row == 1 && col2 == -1 && board.currentPlayer == board.firstPlayer)
			{
				dragging = true;
				dragRegion = Assets.instance.x.region;
				return true;
			}
			// check if valid start of a drag for second player
			if (row == 1 && col3 == 3 && board.currentPlayer == board.secondPlayer)
			{
				dragging = true;
				dragRegion = Assets.instance.o.region;
				return true;
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
		if (dragging == true)
		{
			dragging = false;

			// convert to cell position
			int row = 4 * (height - screenY) / height;
			int col = (int) (viewportWidth * (screenX - 0.5 * width) / width) + 1;

			// if a valid board cell then place piece
			if (row >= 0 && row < 3 && col >= 0 && col < 3 && board.gameState == board.gameState.PLAYING)
			{
				board.move(row, col);
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
