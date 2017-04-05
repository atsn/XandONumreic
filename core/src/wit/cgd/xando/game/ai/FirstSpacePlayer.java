package wit.cgd.xando.game.ai;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.List;

import wit.cgd.xando.game.BasePlayer;
import wit.cgd.xando.game.Board;
import wit.cgd.xando.game.WorldRenderer;
import wit.cgd.xando.game.util.GamePreferences;

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
	public int move()
	{
		Move move = getbestmove();
		return move.x * 3 + move.y;

	}

	private Move getbestmove()
	{

		for (int x = 2; x >= 0; --x)
		{
			for (int y = 0; y < 3; ++y)
			{
				if (board.cells[x][y] == board.EMPTY) return new Move(x, y);
			}
		}

		return null;
	}

}