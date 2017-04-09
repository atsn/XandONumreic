package wit.cgd.xando.game;

import wit.cgd.xando.game.ai.Move;

public abstract class BasePlayer
{
	public boolean human;
	public int mySymbol, opponentSymbol;
	public String name;
	public Board board;
	public int lastPlayedNuber;

	public BasePlayer(Board board, int symbol)
	{
		this.board = board;
		setSymbol(symbol);
		human = false;
	}

	public void setSymbol(int symbol)
	{
		mySymbol = symbol;
		opponentSymbol = (symbol == board.odd) ? board.even : board.odd;
	}

	public abstract Move move();

}
