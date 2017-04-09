package wit.cgd.xando.game;

import wit.cgd.xando.game.ai.MinimaxPlayer;
import wit.cgd.xando.game.ai.Move;
import wit.cgd.xando.game.util.AudioManager;
import wit.cgd.xando.game.util.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Board
{

	@SuppressWarnings("unused")
	private static final String TAG = WorldRenderer.class.getName();
	public boolean isundopressed = false;
	public boolean ishintpressed = false;
	public Move hintmove;

	public static enum GameState
	{
		PLAYING, DRAW, Odd_WON, Even_WON
	}

	public GameState gameState;

	private Stack<Move> previusmoves = new Stack<Move>();
	public final int EMPTY = 16;
	public final int odd = 1;
	public final int even = 2;
	public int[][] cells = new int[3][3];
	public ArrayList<Integer> oddNumbers = new ArrayList<Integer>();
	public ArrayList<Integer> eveNumbers = new ArrayList<Integer>();

	public BasePlayer firstPlayer, secondPlayer;
	public BasePlayer currentPlayer;

	public Board()
	{
		init();
	}

	public void init()
	{}

	public void start()
	{
		for (int r = 0; r < 3; r++)
			for (int c = 0; c < 3; c++)
				cells[r][c] = EMPTY;
		oddNumbers.addAll(Arrays.asList(1, 3, 5, 7, 9));
		eveNumbers.addAll(Arrays.asList(2, 4, 6, 8));
		gameState = GameState.PLAYING;
		currentPlayer = firstPlayer;
	}

	public boolean move()
	{
		return move(-1, -1, -1);

	}

	public void Undo()
	{
		if (firstPlayer.human && secondPlayer.human)
		{
			if (previusmoves.size() > 0)
			{
				Move undomove = previusmoves.pop();
				cells[undomove.row][undomove.col] = EMPTY;

				if (undomove.number % 2 == 0)
				{
					eveNumbers.add(undomove.number);
				}
				else
				{
					oddNumbers.add(undomove.number);
				}

				// switch player
				currentPlayer = (currentPlayer == firstPlayer ? secondPlayer : firstPlayer);

			}
		}

		else

		{

			if (previusmoves.size() > 1)
			{
				Move undomove = previusmoves.pop();
				cells[undomove.row][undomove.col] = EMPTY;

				if (undomove.number % 2 == 0)
				{
					eveNumbers.add(undomove.number);
				}
				else
				{
					oddNumbers.add(undomove.number);
				}

				undomove = previusmoves.pop();
				cells[undomove.row][undomove.col] = EMPTY;

				if (undomove.number % 2 == 0)
				{
					eveNumbers.add(undomove.number);
				}
				else
				{
					oddNumbers.add(undomove.number);
				}

			}

		}

	}

	public void Dohint()
	{
		BasePlayer hintplayer = new MinimaxPlayer(this, currentPlayer.mySymbol, 4);
		hintmove = hintplayer.move();
		cells[hintmove.row][hintmove.col] = hintmove.number;

	}
	
	public void undohint()
	{
		cells[hintmove.row][hintmove.col] = EMPTY;
		hintmove = null;

	}

	public boolean move(int row, int col, int number)
	{

		if (currentPlayer.human)
		{
			if (row < 0 || col < 0 || row > 2 || col > 2 || cells[row][col] != EMPTY || (!oddNumbers.contains(number) && !eveNumbers.contains(number))) return false;
			AudioManager.instance.play(Assets.instance.sounds.first);
			previusmoves.push(new Move(row, col, number, 0));
		}
		else
		{ // computer player
			Move move = currentPlayer.move();
			col = move.col;
			row = move.row;
			number = move.number;
			previusmoves.push(move);
			AudioManager.instance.play(Assets.instance.sounds.second);
		}

		System.out.println(" " + currentPlayer.human + " " + row + " " + col);
		// store move
		cells[row][col] = number;
		if (currentPlayer.mySymbol == odd)
		{
			for (int i = 0; i < oddNumbers.size(); i++)
			{
				if (oddNumbers.get(i) == number)
				{
					oddNumbers.remove(i);
				}
			}

		}
		if (currentPlayer.mySymbol == even)
		{
			for (int i = 0; i < eveNumbers.size(); i++)
			{
				if (eveNumbers.get(i) == number)
				{
					eveNumbers.remove(i);
				}
			}
		}

		System.out.print("Board:");
		for (int r = 0; r < 3; r++)
			for (int c = 0; c < 3; c++)
				System.out.print(" " + cells[r][c]);
		System.out.println();
		if (hasWon(currentPlayer.mySymbol, row, col))
		{
			gameState = currentPlayer.mySymbol == odd ? GameState.Odd_WON : GameState.Even_WON;
			if (currentPlayer.human)
			{
				AudioManager.instance.play(Assets.instance.sounds.win);
			}
			else
			{
				AudioManager.instance.play(Assets.instance.sounds.win);
			}

		}
		else if (isDraw())
		{
			gameState = GameState.DRAW;
			AudioManager.instance.play(Assets.instance.sounds.draw);
		}

		// switch player
		if (gameState == GameState.PLAYING)
		{
			currentPlayer = (currentPlayer == firstPlayer ? secondPlayer : firstPlayer);
		}

		return true;
	}

	public boolean isDraw()
	{
		for (int r = 0; r < 3; ++r)
		{
			for (int c = 0; c < 3; ++c)
			{
				if (cells[r][c] == EMPTY)
				{
					return false; // an empty seed found, not a draw, exit
				}
			}
		}
		return true; // no empty cell, it's a draw
	}

	public boolean hasWon(int symbol, int row, int col)
	{
		return (
		// 3-in-the-row
		cells[row][0] + cells[row][1] + cells[row][2] == 15 || // 3-in-the-column
				cells[0][col] + cells[1][col] + cells[2][col] == 15 || // 3-in-the-diagonal
				row == col && cells[0][0] + cells[1][1] + cells[2][2] == 15 || // 3-in-the-opposite-diagonal
				row + col == 2 && cells[0][2] + cells[1][1] + cells[2][0] == 15);
	}

	public void render(SpriteBatch batch)
	{
		TextureRegion region = Assets.instance.board.region;
		batch.draw(region.getTexture(), -2, -Constants.VIEWPORT_HEIGHT / 2 + 0.1f, 0, 0, 4, 4, 1, 1, 0, region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
				false, false);

		if (ishintpressed)
		{
			// draw hintdn button
			region = Assets.instance.hintdn.region;
			batch.draw(region.getTexture(), (3f) * 1.4f - 1.9f, 0f * 1.4f - 2.3f, 0, 0, 1, 0.5f, 0.5f, 1, 0, region.getRegionX(), region.getRegionY(), region.getRegionWidth(),
					region.getRegionHeight(), false, false);
		}

		else
		{
			// draw hintup button
			region = Assets.instance.hintup.region;
			batch.draw(region.getTexture(), (3f) * 1.4f - 1.9f, 0f * 1.4f - 2.3f, 0, 0, 1, 0.5f, 0.5f, 1, 0, region.getRegionX(), region.getRegionY(), region.getRegionWidth(),
					region.getRegionHeight(), false, false);
		}

		if (isundopressed)
		{
			// draw undodn button
			region = Assets.instance.undodn.region;
			batch.draw(region.getTexture(), (3.6f) * 1.4f - 1.9f, 0f * 1.4f - 2.3f, 0, 0, 1, 0.5f, 0.5f, 1, 0, region.getRegionX(), region.getRegionY(), region.getRegionWidth(),
					region.getRegionHeight(), false, false);
		}

		else
		{
			// draw undoup button
			region = Assets.instance.undoup.region;
			batch.draw(region.getTexture(), (3.6f) * 1.4f - 1.9f, 0f * 1.4f - 2.3f, 0, 0, 1, 0.5f, 0.5f, 1, 0, region.getRegionX(), region.getRegionY(), region.getRegionWidth(),
					region.getRegionHeight(), false, false);
		}

		// draw drag and drop pieces
		if (oddNumbers.contains(1))
		{
			region = Assets.instance.number1.region;
			batch.draw(region.getTexture(), (-1) * 1.4f - 1.9f, 1.3f * 1.4f - 2.3f, 0, 0, 1, 0.5f, 0.5f, 1, 0, region.getRegionX(), region.getRegionY(), region.getRegionWidth(),
					region.getRegionHeight(), false, false);
		}
		if (oddNumbers.contains(3))
		{
			region = Assets.instance.number3.region;
			batch.draw(region.getTexture(), (-1) * 1.4f - 1.9f, 1.8f * 1.4f - 2.3f, 0, 0, 1, 0.5f, 0.5f, 1, 0, region.getRegionX(), region.getRegionY(), region.getRegionWidth(),
					region.getRegionHeight(), false, false);
		}
		if (oddNumbers.contains(5))
		{
			region = Assets.instance.number5.region;
			batch.draw(region.getTexture(), (-1) * 1.4f - 1.9f, 0.8f * 1.4f - 2.3f, 0, 0, 1, 0.5f, 0.5f, 1, 0, region.getRegionX(), region.getRegionY(), region.getRegionWidth(),
					region.getRegionHeight(), false, false);
		}
		if (oddNumbers.contains(7))
		{
			region = Assets.instance.number7.region;
			batch.draw(region.getTexture(), (-1) * 1.4f - 1.9f, 0.3f * 1.4f - 2.3f, 0, 0, 1, 0.5f, 0.5f, 1, 0, region.getRegionX(), region.getRegionY(), region.getRegionWidth(),
					region.getRegionHeight(), false, false);
		}
		if (oddNumbers.contains(9))
		{
			region = Assets.instance.number9.region;
			batch.draw(region.getTexture(), (-1) * 1.4f - 1.9f, 2.3f * 1.4f - 2.3f, 0, 0, 1, 0.5f, 0.5f, 1, 0, region.getRegionX(), region.getRegionY(), region.getRegionWidth(),
					region.getRegionHeight(), false, false);
		}

		if (eveNumbers.contains(2))
		{
			region = Assets.instance.number2.region;
			batch.draw(region.getTexture(), (3) * 1.4f - 1.9f, 1.5f * 1.4f - 2.3f, 0, 0, 1, 0.5f, 0.5f, 1, 0, region.getRegionX(), region.getRegionY(), region.getRegionWidth(),
					region.getRegionHeight(), false, false);
		}
		if (eveNumbers.contains(4))
		{
			region = Assets.instance.number4.region;
			batch.draw(region.getTexture(), (3) * 1.4f - 1.9f, 2f * 1.4f - 2.3f, 0, 0, 1, 0.5f, 0.5f, 1, 0, region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
					false, false);
		}
		if (eveNumbers.contains(6))
		{
			region = Assets.instance.number6.region;
			batch.draw(region.getTexture(), (3) * 1.4f - 1.9f, 1f * 1.4f - 2.3f, 0, 0, 1, 0.5f, 0.5f, 1, 0, region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
					false, false);
		}
		if (eveNumbers.contains(8))
		{
			region = Assets.instance.number8.region;
			batch.draw(region.getTexture(), (3) * 1.4f - 1.9f, 0.5f * 1.4f - 2.3f, 0, 0, 1, 0.5f, 0.5f, 1, 0, region.getRegionX(), region.getRegionY(), region.getRegionWidth(),
					region.getRegionHeight(), false, false);

		}

		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 3; col++)
			{
				if (cells[row][col] == EMPTY) continue;
				switch (cells[row][col])
				{

				case 1:
					region = Assets.instance.number1.region;
					break;
				case 2:
					region = Assets.instance.number2.region;
					break;
				case 3:
					region = Assets.instance.number3.region;
					break;
				case 4:
					region = Assets.instance.number4.region;
					break;
				case 5:
					region = Assets.instance.number5.region;
					break;
				case 6:
					region = Assets.instance.number6.region;
					break;
				case 7:
					region = Assets.instance.number7.region;
					break;
				case 8:
					region = Assets.instance.number8.region;
					break;
				case 9:
					region = Assets.instance.number9.region;
					break;
				}
				batch.draw(region.getTexture(), col * 1.4f - 1.9f, row * 1.4f - 2.3f, 0, 0, 1, 1, 1, 1, 0, region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
						false, false);
			}

	}

}
