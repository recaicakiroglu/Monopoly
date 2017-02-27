/**
 * 
 * @author Recai Cakiroglu
 * @version 1.0
 */
public class Player {
	private String Name;
	private int Money;
	private int pos;
	private int jail;

	/**
	 * Player Constructor
	 * 
	 * @param name
	 *            Player Name
	 * @param money
	 *            Player Money
	 * @param pos
	 *            Player Position on game board
	 * @param jail
	 *            is player in jail or not?
	 */
	public Player(String name, int money, int pos, int jail) {
		Name = name;
		Money = money;
		this.pos = pos;
		this.jail = jail;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getMoney() {
		return Money;
	}

	public void setMoney(int money) {
		Money = money;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public int getJail() {
		return jail;
	}

	public void setJail(int jail) {
		this.jail = jail;
	}

}
