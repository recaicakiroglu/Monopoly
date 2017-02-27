
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * 
 * @author Recai Cakiroglu
 * @version 1.0
 */
public class InputReader {

	/**
	 * this class fill empty squares one by one
	 * 
	 * @param AL
	 *            Board List with 40 empty square
	 * @param cardtype_chance
	 *            chance cards
	 * @param cardtype_community
	 *            community chest cards
	 */
	public void Json_Reader(ArrayList<Board> AL, Queue<String> cardtype_chance, Queue<String> cardtype_community) {
		JSONParser parser = new JSONParser();
		try {
			// reading list.json
			Object list = parser.parse(new FileReader("list.json"));
			JSONObject jsonlistObject = (JSONObject) list;
			JSONArray chance = (JSONArray) jsonlistObject.get("chanceList");
			JSONArray community = (JSONArray) jsonlistObject.get("communityChestList");
			@SuppressWarnings("unchecked")
			Iterator<JSONObject> iterator_chance = chance.iterator();
			@SuppressWarnings("unchecked")
			Iterator<JSONObject> iterator_community = community.iterator();
			while (iterator_chance.hasNext()) {
				JSONObject line = iterator_chance.next();
				String name = (String) line.get("item");
				cardtype_chance.add(name);
			}
			while (iterator_community.hasNext()) {
				JSONObject line = iterator_community.next();
				String name = (String) line.get("item");
				cardtype_community.add(name);
			}
			// reading property.json
			Object obj = parser.parse(new FileReader("property.json"));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray Land = (JSONArray) jsonObject.get("1");
			JSONArray Rail = (JSONArray) jsonObject.get("2");
			JSONArray Company = (JSONArray) jsonObject.get("3");
			@SuppressWarnings("unchecked")
			Iterator<JSONObject> iterator_Land = Land.iterator();
			@SuppressWarnings("unchecked")
			Iterator<JSONObject> iterator_Rail = Rail.iterator();
			@SuppressWarnings("unchecked")
			Iterator<JSONObject> iterator_Company = Company.iterator();
			// fills empty squares
			while (iterator_Land.hasNext()) {
				JSONObject line = iterator_Land.next();
				String tempcost = (String) line.get("cost");
				int cost = Integer.parseInt(tempcost);
				String name = (String) line.get("name");

				String tempid = (String) line.get("id");
				int id = Integer.parseInt(tempid);
				Board temp = new Land(id, name, cost, "Empty");
				AL.add(temp);
			}
			while (iterator_Rail.hasNext()) {
				JSONObject line = iterator_Rail.next();
				String tempcost = (String) line.get("cost");
				int cost = Integer.parseInt(tempcost);
				String name = (String) line.get("name");
				String tempid = (String) line.get("id");
				int id = Integer.parseInt(tempid);

				Board temp = new Rail(id, name, cost, "Empty");
				AL.add(temp);
			}
			while (iterator_Company.hasNext()) {
				JSONObject line = iterator_Company.next();
				String tempcost = (String) line.get("cost");
				int cost = Integer.parseInt(tempcost);
				String name = (String) line.get("name");
				String tempid = (String) line.get("id");
				int id = Integer.parseInt(tempid);
				Board temp = new Company(id, name, cost, "Empty");
				AL.add(temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param STR
	 *            args[0]-command.txt
	 * @return returns one simple string which all lines included
	 * @throws IOException
	 *             if file doesn't exists
	 */
	public String CommandReader(String STR) throws IOException {

		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(STR), "UTF-8"));

		String line;
		StringBuffer stringBuffer = new StringBuffer();
		while ((line = bufferedReader.readLine()) != null) {
			stringBuffer.append(line);
			stringBuffer.append("\n");
		}
		String text = stringBuffer.toString();
		return text;
	}

	/**
	 * this class fill empty action squares
	 * 
	 * @param AL
	 *            Board with missing Action Squares
	 */
	public void addmissingslot(ArrayList<Board> AL) {
		AL.add(new Miss(0, "Null", 0, "NP"));
		AL.add(new Miss(1, "GO", 0, "NP"));
		AL.add(new Miss(3, "Community Chest", 0, "NP"));
		AL.add(new Miss(5, "Income Tax", 0, "NP"));
		AL.add(new Miss(8, "Chance", 0, "NP"));
		AL.add(new Miss(11, "Jail", 0, "NP"));
		AL.add(new Miss(18, "Community Chest", 0, "NP"));
		AL.add(new Miss(21, "Free Parking", 0, "NP"));
		AL.add(new Miss(23, "Chance", 0, "NP"));
		AL.add(new Miss(31, "Go to Jail", 0, "NP"));
		AL.add(new Miss(34, "Community Chest", 0, "NP"));
		AL.add(new Miss(37, "Chance", 0, "NP"));
		AL.add(new Miss(39, "Super Tax", 0, "NP"));

	}

	/**
	 * show method if command line is equal to show() or the game is over it
	 * will print last status and winner
	 * 
	 * @param writer
	 *            writer for output.txt
	 * @param board
	 *            game board
	 * @param P1
	 *            Player 1
	 * @param P2
	 *            Player 2
	 * @param Banker
	 *            Banker
	 */
	public void show(PrintWriter writer, ArrayList<Board> board, Player P1, Player P2, Player Banker) {
		int writecontrol = 0;
		writer.println(
				"-----------------------------------------------------------------------------------------------------------");
		writer.printf(P1.getName() + "\t" + P1.getMoney() + "\t" + "have: ");
		for (int j = 1; j < 40; j++) {
			if (board.get(j).getStatus().equals(P1.getName())) {
				if (writecontrol == 0) {
					writer.write(board.get(j).getName());
					writecontrol = 1;
				} else {
					writer.write("," + board.get(j).getName());
				}

			}
		}
		writecontrol = 0;
		writer.write("\r\n");
		writer.printf(P2.getName() + "\t" + P2.getMoney() + "\t" + "have: ");
		for (int j = 1; j < 40; j++) {
			if (board.get(j).getStatus().equals(P2.getName())) {
				if (writecontrol == 0) {
					writer.write(board.get(j).getName());
					writecontrol = 1;
				} else {
					writer.write("," + board.get(j).getName());
				}
			}
		}
		writecontrol = 0;
		writer.write("\r\n");

		writer.println(Banker.getName() + "\t" + Banker.getMoney());

		if (P1.getMoney() > P2.getMoney()) {
			writer.println("Winner " + P1.getName());
		} else {
			writer.println("Winner " + P2.getName());
		}
		writer.println(
				"-----------------------------------------------------------------------------------------------------------");
	}
}
