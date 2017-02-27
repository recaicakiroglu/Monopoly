
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * @author Recai Cakiroglu
 * @version 1.0
 */
public class Main {
	/**
	 * main class has 3 mission read inputs - handle command file - print output
	 * 
	 * @param args
	 *            args for input files
	 * 
	 * @throws IOException
	 *             if file doesn't exists
	 */
	public static void main(String[] args) throws IOException {
		// Players and Banker
		Player P1 = new Player("Player 1", 15000, 1, 0);
		Player P2 = new Player("Player 2", 15000, 1, 0);
		Player Banker = new Player("Banker", 100000, 0, 0);
		// Reading inputs
		InputReader inputObj = new InputReader();
		Move moveObj = new Move();
		// Board , Chance and Community Cards
		ArrayList<Board> board = new ArrayList<Board>();
		Queue<String> chance = new LinkedList<String>();
		Queue<String> communitychest = new LinkedList<String>();
		// creating Output file
		File fileObj = new File("output.txt");
		// writer for output.txt
		FileWriter fileWriterObj = new FileWriter(fileObj.getAbsoluteFile());
		PrintWriter writer = new PrintWriter(fileWriterObj);
		inputObj.Json_Reader(board, chance, communitychest);
		inputObj.addmissingslot(board);
		Collections.sort(board);
		String commandsTemp = inputObj.CommandReader(args[0]);
		String[] Commands = commandsTemp.split("\n");
		int finish = 0;
		// For statement for every line in command.txt
		for (int i = 0; i < Commands.length; i++) {
			if (finish == 1) {
				continue;
			} else {
				if (Commands[i].equals("show()")) {
					inputObj.show(writer, board, P1, P2, Banker);
				} else {
					String Dice[] = Commands[i].split(";");
					int dice = Integer.parseInt(Dice[1]);

					if (Dice[0].equals("Player 1")) {
						finish = moveObj.StepbyStep(board, dice, writer, P1, P2, Banker, P1, P2, chance,
								communitychest);

					}
					if (Dice[0].equals("Player 2")) {
						finish = moveObj.StepbyStep(board, dice, writer, P2, P1, Banker, P1, P2, chance,
								communitychest);
					}
				}
			}

		}
		// game over print last status and winner
		inputObj.show(writer, board, P1, P2, Banker);

		writer.close();
	}

}
