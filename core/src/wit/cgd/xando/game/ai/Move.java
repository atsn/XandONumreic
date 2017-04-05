package wit.cgd.xando.game.ai;

public class Move
{
	public int x;
	public int y;
	public int score;

	public Move(int X, int Y, int Score)
	{
		score = Score;
		x = X;
		y = Y;
	}
	
	public Move(int Score)
	{
		score = Score;
		x = 0;
		y = 0;
	}
	
	public Move(int X, int Y)
	{
		score = 0;
		x = X;
		y = Y;
	}
}
