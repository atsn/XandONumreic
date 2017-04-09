package wit.cgd.xando.game.ai;

public class Move
{
	public int row;
	public int col;
	public int score;
	public int number;

	public Move(int row, int col, int score)
	{
		this.score = score;
		this.row = row;
		this.col = col;
	}
	
	public Move(int score)
	{
		this.score = score;
		row = 0;
		col = 0;
	}
	
	public Move(int row, int col)
	{
		score = 0;
		this.row =row;
		this.col = col;
	}
	public Move(int row, int col,int number, int score)
	{
		this.number = number;
		this.score = score;
		this.row = row;
		this.col = col;
	}
}
