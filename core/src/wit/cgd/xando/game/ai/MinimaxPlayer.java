package wit.cgd.xando.game.ai;

import java.util.ArrayList;
import java.util.Random;
import wit.cgd.xando.game.BasePlayer;
import wit.cgd.xando.game.Board;

public class MinimaxPlayer extends BasePlayer
{

	private Random randomGenerator;
	int skill;

	public MinimaxPlayer(Board board, int symbol, int Skill)
	{
		super(board, symbol);
		randomGenerator = new Random();
		skill = Skill; // skill is measure of search depth
	
	}

	@Override
	public Move move()
	{
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		Move move = minimax(mySymbol, opponentSymbol, 0);
		return move;
	}

	private Move minimax(int p_mySymbol, int p_opponentSymbol, int depth)
	{
		
		ArrayList<Integer> numbers;
		boolean isreachet = false;
		int returnnumber = -1;
		if (p_mySymbol == board.odd)
		{
			numbers = board.oddNumbers;
		}
		else
		{
			numbers = board.eveNumbers;
		}
		final float WIN_SCORE = 100;
		final float DRAW_SCORE = 0;

		float score;
		float maxScore = -10000;
		int maxPos = -1;

		// for each board position
		for (int r = 0; r < 3; ++r)
		{
			for (int c = 0; c < 3; ++c)
			{
				// skip over used positions
				if (board.cells[r][c] != board.EMPTY) continue;

				for (int i = 0; i < numbers.size(); i++)
				{

					int number = numbers.get(i);
					// place move
					board.cells[r][c] = number;

					// evaluate board (recursively)
					if (p_mySymbol == board.odd)
					{
						board.oddNumbers.remove(i);
					}
					else
					{
						board.eveNumbers.remove(i);
					}

					if (board.hasWon(p_mySymbol, r, c))
					{
						score = WIN_SCORE;
					}

					else if (board.isDraw())
					{
						score = DRAW_SCORE;
					}

					else
					{

						if (depth < skill)
						{
							score = -minimax(p_opponentSymbol, p_mySymbol, depth + 1).score;
						}
						else
						{
							score = 0;
						}
					}

					if (p_mySymbol == board.odd)
					{
						board.oddNumbers.add(i, number);
					}
					else
					{
						board.eveNumbers.add(i, number);
					}

					// update ranking
					if (maxScore - score < 1 && maxScore-score > -0.1 && randomGenerator.nextDouble() < 0.49)
					{
						maxScore = score;
						maxPos = 3 * r + c;
						returnnumber = number;
						isreachet = true;
					}

					else if (score > maxScore)
					{ // clear
						maxScore = score;
						maxPos = 3 * r + c;
						returnnumber = number;
						isreachet = true;
					}

				// Gdx.app.log("\t", "Score "+score+ "Debth"+ depth);

					// undo move

					board.cells[r][c] = board.EMPTY;

				}
			}
		}
		
		if (!isreachet)
		{
			maxScore = 0;
		}

		// on uppermost call return move not score
		return new Move(maxPos / 3, maxPos % 3, returnnumber, (int) maxScore);

	};

}