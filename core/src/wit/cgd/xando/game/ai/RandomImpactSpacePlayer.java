package wit.cgd.xando.game.ai;

import java.util.Random;

import sun.invoke.empty.Empty;
import wit.cgd.xando.game.BasePlayer;
import wit.cgd.xando.game.Board;
import wit.cgd.xando.game.WorldRenderer;

public class RandomImpactSpacePlayer extends BasePlayer
{
	@SuppressWarnings("unused")
	private static final String TAG = WorldRenderer.class.getName();

	public RandomImpactSpacePlayer(Board board, int symbol)
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
		int[] empty = new int[4];
		int i = 0;
		empty[0] = -1;
		if (board.cells[1][1] == board.EMPTY)
		{
			return new Move(1, 1);
		}

		for (int y : new int[] { 0, 2 })
		{
			for (int x : new int[] { 0, 2 })
			{
				if (board.cells[x][y] == board.EMPTY)
				{
					empty[i] = x * 3 + y;
					i++;
				}

			}
		}

		if (empty[0] != -1)
		{
			for (; i < 4; i++)
			{
				empty[i] = -1;
				
			}

			int x;
			int y;
			do
			{
				Random random = new Random(System.currentTimeMillis());
				x = random.nextInt(3);
				y = random.nextInt(3);
			}
			while (empty[0] != x * 3 + y && empty[1] != x * 3 + y && empty[2] != x * 3 + y && empty[3] != x * 3 + y );
			return new Move(x,y);
		}


		int x;
		int y;
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
