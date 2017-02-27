/**
 * 
 * @author Recai Cakiroglu
 * @version 1.0
 */
public abstract class Board implements Comparable<Board> {
	private int id;
	private String name;
	private int cost;
	private String status;

	/**
	 * Constructor for Board Class
	 * 
	 * @param id
	 *            Board Square ID
	 * @param name
	 *            Board Square Name
	 * @param cost
	 *            Board Square Money
	 * @param status
	 *            Board Square Status(EMPTY,P1 OWNER,P2 OWNER)
	 */
	public Board(int id, String name, int cost, String status) {
		this.id = id;
		this.name = name;
		this.cost = cost;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * if board square is a land square then calculate money for rent
	 * 
	 * @return rent
	 */
	public int getRent() {
		if (this instanceof Land) {
			if (this.getCost() <= 2000) {
				return (int) (this.getCost() * 0.4);
			}
			if (2001 <= this.getCost() && this.getCost() <= 3000) {
				return (int) (this.getCost() * 0.3);
			}
			if (3001 <= this.getCost() && this.getCost() <= 4000) {
				return (int) (this.getCost() * 0.35);
			}
		}
		return 0;

	}

	@Override
	public int compareTo(Board o) {
		return this.getId() - o.getId();

	}

}

class Land extends Board {

	public Land(int id, String name, int cost, String status) {
		super(id, name, cost, status);
		// TODO Auto-generated constructor stub
	}

}

class Rail extends Board {

	public Rail(int id, String name, int cost, String status) {
		super(id, name, cost, status);
		// TODO Auto-generated constructor stub
	}

}

class Company extends Board {

	public Company(int id, String name, int cost, String status) {
		super(id, name, cost, status);
		// TODO Auto-generated constructor stub
	}

}

class Miss extends Board {

	public Miss(int id, String name, int cost, String status) {
		super(id, name, cost, status);
		// TODO Auto-generated constructor stub
	}

}
