package wit.cgd.xando.game.ai;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.scenes.scene2d.ui.List;

import wit.cgd.xando.game.BasePlayer;
import wit.cgd.xando.game.Board;
import wit.cgd.xando.game.WorldRenderer;
import wit.cgd.xando.game.util.GamePreferences;

public class RandomSpacePlayer extends BasePlayer
{

	@SuppressWarnings("unused")
	private static final String TAG = WorldRenderer.class.getName();

	public RandomSpacePlayer(Board board, int symbol)
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