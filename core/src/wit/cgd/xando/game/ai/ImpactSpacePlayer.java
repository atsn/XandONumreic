package wit.cgd.xando.game.ai;

import java.util.Random;

import sun.invoke.empty.Empty;
import wit.cgd.xando.game.BasePlayer;
import wit.cgd.xando.game.Board;
import wit.cgd.xando.game.WorldRenderer;

public class ImpactSpacePlayer extends BasePlayer
{
	@SuppressWarnings("unused")
	private static final String TAG = WorldRenderer.class.getName();

	public ImpactSpacePlayer(Board board, int symbol)
	{
		super(board, symbol);
		name = "FirstSpacePlayer";
	}

	@Override
	public int move()
	{
		Move move = getbestmove();
		return move.x * 3 + move.y;

	}

	private Move getbestmove()
	{
		for (int x : new int[] { 1 })
		{
			for (int y : new int[] { 1 })
			{
				if (board.cells[x][y] == board.EMPTY) return new Move(x, y);
			}
		}
		
		for (int y : new int[] { 0,2 })
		{
			for (int x : new int[] { 0,2 })
			{
				if (board.cells[x][y] == board.EMPTY) return new Move(x, y);
			}
		}
		

		int x = -1;
		int y = -1;
		do
		{
			Random random = new Random(System.currentTimeMillis());
			x = random.nextInt(3);
			y = random.nextInt(3);
		}
		while (board.cells[x][y] != board.EMPTY);
		return new Move(x, y);
	}

}
