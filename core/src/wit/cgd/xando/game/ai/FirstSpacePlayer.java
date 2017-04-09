package wit.cgd.xando.game.ai;

import wit.cgd.xando.game.BasePlayer;
import wit.cgd.xando.game.Board;
import wit.cgd.xando.game.WorldRenderer;

public class FirstSpacePlayer extends BasePlayer
{

	@SuppressWarnings("unused")
	private static final String TAG = WorldRenderer.class.getName();

	public FirstSpacePlayer(Board board, int symbol)
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

		for (int x = 2; x >= 0; --x)
		{
			for (int y = 0; y < 3; ++y)
			{
				if (mySymbol == board.odd)
				{
					if (board.cells[x][y] == board.EMPTY) return new Move(x, y,board.oddNumbers.get(0),0);
				}
				
				if (mySymbol == board.even )
				{
					if (board.cells[x][y] == board.EMPTY) return new Move(x, y,board.eveNumbers.get(0),0);
				}
			}
		}

		return null;
	}

}