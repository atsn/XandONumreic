package wit.cgd.xando.game.ai;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.scenes.scene2d.ui.List;

import wit.cgd.xando.game.BasePlayer;
import wit.cgd.xando.game.Board;
import wit.cgd.xando.game.WorldRenderer;
import wit.cgd.xando.game.util.GamePreferences;

public class MinimaxPlayer extends BasePlayer
{

	@SuppressWarnings("unused")
	private static final String TAG = WorldRenderer.class.getName();

	private Random randomGenerator;
	public int skill;
	public int i = 0;

	public MinimaxPlayer(Board board, int symbol, int Skill)
	{
		super(board, symbol);
		skill = Skill;
		name = "FirstSpacePlayer";
		randomGenerator = new Random();
	}

	@Override
	public int move()
	{

		Move move = getbestmove(-1, -1, true, (int) skill);
		return move.x * 3 + move.y;

	}

	// get best move sorce https://www.youtube.com/watch?v=CwziaVrM_vc
	private Move getbestmove(int lastmoveX, int LastMoveY, boolean Isme, int numberOfRecursions)
	{
		i++;
		numberOfRecursions--;

		if (lastmoveX >= 0 && LastMoveY >= 0)
		{
			if (board.hasWon(mySymbol, lastmoveX, LastMoveY)) return new Move(10);
			if (board.hasWon(opponentSymbol, lastmoveX, LastMoveY)) return new Move(-10);
			if (board.isDraw()) return new Move(0);
		}
		if (numberOfRecursions == 0)
		{
			for (int x = 2; x >= 0; --x)
			{
				for (int y = 0; y < 3; ++y)
				{
					if (board.cells[x][y] == board.EMPTY) return new Move(x, y, 0);
				}
			}
		}

		ArrayList<Move> moves = new ArrayList<Move>();
		for (int x = 2; x >= 0; --x)
		{
			for (int y = 0; y < 3; ++y)
			{
				if (board.cells[x][y] != board.EMPTY) continue;

				Move move = new Move(x, y);
				if (Isme) board.cells[x][y] = mySymbol;
				else board.cells[x][y] = opponentSymbol;

				if (Isme)
				{
					move.score = +getbestmove(x, y, false, numberOfRecursions).score;
				}
				if (!Isme)
				{
					move.score = +getbestmove(x, y, true, numberOfRecursions).score;
				}
				moves.add(move);
				board.cells[x][y] = board.EMPTY;

			}
		}

		Move bestmove = null;
		if (Isme)
		{

			int bestscore = Integer.MIN_VALUE;
			for (Move move : moves)
			{
				if (move.score > bestscore)
				{
					bestscore = move.score;
					bestmove = move;
				}
			}

		}

		else
		{
			int bestscore = Integer.MAX_VALUE;
			for (Move move : moves)
			{
				if (move.score < bestscore)
				{
					bestscore = move.score;
					bestmove = move;
				}
			}

		}
		System.out.print(i + "\n");
		return bestmove;
	}

}