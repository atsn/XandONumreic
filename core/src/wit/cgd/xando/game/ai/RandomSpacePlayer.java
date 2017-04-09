package wit.cgd.xando.game.ai;

import java.util.Random;
import wit.cgd.xando.game.BasePlayer;
import wit.cgd.xando.game.Board;
import wit.cgd.xando.game.WorldRenderer;

public class RandomSpacePlayer extends BasePlayer
{

	@SuppressWarnings("unused")
	private static final String TAG = WorldRenderer.class.getName();

	public RandomSpacePlayer(Board board, int symbol)
	{
		super(board, symbol);
		name = "RandomSpacePlayer";
	}

	@Override
	public Move move()
	{
		return getbestmove();
	}

	private Move getbestmove()
	{
		Random random = new Random();
		int x = -1;
		int y = -1;
		int number = -1;
		
		if (mySymbol == board.odd)
		{
			number = board.oddNumbers.get(random.nextInt(board.oddNumbers.size()));
		}
		if (mySymbol == board.even)
		{
			number = board.eveNumbers.get(random.nextInt(board.eveNumbers.size()));
		}

		do
		{

			x = random.nextInt(3);
			y = random.nextInt(3);

		}
		while (board.cells[x][y] != board.EMPTY);
		return new Move(x, y, number, 0);
	}

}