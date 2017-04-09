package wit.cgd.xando.game.ai;

import java.util.ArrayList;
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
	public Move move()
	{
		return getbestmove();

	}

	private Move getbestmove()
	{
		ArrayList<Integer> numbers;

		if (mySymbol == board.odd)
		{
			numbers = board.eveNumbers;
		}
		else
		{
			numbers = board.oddNumbers;
		}

		BasePlayer tempplayer = new MinimaxPlayer(board, mySymbol, 0);
		Move returnmove = tempplayer.move();

		if (returnmove.score == 100)
		{
			return returnmove;
		}
		else
		{
			for (int r = 0; r < 3; ++r)
			{
				for (int c = 0; c < 3; ++c)
				{
					// skip over used positions
					if (board.cells[r][c] != board.EMPTY) continue;

					for (int i = 0; i < numbers.size(); i++)
					{
						board.cells[r][c] = numbers.get(i);
						if (board.hasWon(opponentSymbol, r, c))
						{
							board.cells[r][c] = board.EMPTY;
							returnmove = new Move(r, c, board.eveNumbers.get(0), 0);
						}
						board.cells[r][c] = board.EMPTY;
					}
				}
			}
			
		}
		
		tempplayer = new RandomSpacePlayer(board, mySymbol);
		returnmove = tempplayer.move();
		return returnmove;

	}
}
