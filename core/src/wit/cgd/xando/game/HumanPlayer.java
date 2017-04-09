package wit.cgd.xando.game;

import wit.cgd.xando.game.ai.Move;

public class HumanPlayer extends BasePlayer
{

	@SuppressWarnings("unused")
	private static final String TAG = WorldRenderer.class.getName();

	public HumanPlayer(Board board, int symbol)
	{
		super(board, symbol);
		human = true;
		name = "Electrified Meat";
	}

	@Override
	public Move move()
	{
		// human move handled in worldController
		return new Move(1);
	}

}
