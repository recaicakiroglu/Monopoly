
import java.io.PrintWriter;
import java.util.ArrayList;

import java.util.Queue;

/**
 * 
 * @author Recai Cakiroglu
 * @version 1.0
 */
public class Move {
	String jailp1 = "Empty";
	String jailp2 = "Empty";
	int jail1counter = 0;
	int jail2counter = 0;
	int Leicester = 0;
	int cont = 3;

	/**
	 * 
	 * 
	 * 
	 * @param AL
	 *            Game Board with full squares
	 * @param Dice
	 *            Current Player's dice
	 * @param writer
	 *            writer for output
	 * @param player
	 *            Current Player
	 * @param other
	 *            Other Player (waiting)
	 * @param Bank
	 *            Bank
	 * @param player1
	 *            Player 1 (p1 called already but this one is for show method)
	 * @param player2
	 *            Player 2 (p2 called already but this one is for show method)
	 * @param cardtype_chance
	 *            chance cards
	 * @param cardtype_community
	 *            community chest cards
	 * @return integer checks bankrupt situation
	 */

	public int StepbyStep(ArrayList<Board> AL, int Dice, PrintWriter writer, Player player, Player other, Player Bank,
			Player player1, Player player2, Queue<String> cardtype_chance, Queue<String> cardtype_community) {

		int Place = player.getPos() + Dice;
		int railcounter = 0;
		boolean nocash = false;
		if (Place > 40) {
			Place = Place - 40;
			player.setMoney(player.getMoney() + 200);
			Bank.setMoney(Bank.getMoney() - 200);
		}

		// Rail Count

		for (int i = 1; i < 40; i++) {
			if (AL.get(i) instanceof Rail) {
				if (AL.get(i).getStatus().equals(other.getName())) {
					railcounter++;
				}
			}
		}

		player.setPos(Place);

		if (jailp1 != "Empty") {
			if (jailp1.equals(player.getName())) {
				player.setPos(11);
				cont = 2;
				writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t" + player1.getMoney()
						+ "\t" + player2.getMoney() + "\t" + player.getName() + " " + "in jail (count=" + jail1counter
						+ ")");
				jail1counter++;
				if (jail1counter == 4) {
					jailp1 = "Empty";
					cont = 5;
					jail1counter = 0;
				}
			} else {
				cont = 3;
			}

		}
		if (jailp2 != "Empty") {
			if (jailp2.equals(player.getName())) {
				player.setPos(11);
				cont = 2;
				writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t" + player1.getMoney()
						+ "\t" + player2.getMoney() + "\t" + player.getName() + " " + "in jail (count=" + jail2counter
						+ ")");
				jail2counter++;
				if (jail2counter == 4) {
					jailp2 = "Empty";
					cont = 5;
					jail2counter = 0;
				}
			} else {
				cont = 3;
			}

		}
		if (cont == 3) {

			if (AL.get(Place).getName().equals("Jail")) {
				if (player.getName().equals("Player 1")) {
					jailp1 = player.getName();
					jail1counter++;
				}
				if (player.getName().equals("Player 2")) {
					jailp2 = player.getName();
					jail2counter++;
				}

				writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t" + player1.getMoney()
						+ "\t" + player2.getMoney() + "\t" + player.getName() + " " + "went to jail");

			}
			if (AL.get(Place).getName().equals("GO")) {

				writer.println(player.getName() + "\t" + Dice + "\t" + Place + "\t" + player1.getMoney() + "\t"
						+ player2.getMoney() + "\t" + player.getName() + " is in GO square");

			}
			if (AL.get(Place).getName().equals("Free Parking")) {
				writer.println(player.getName() + "\t" + Dice + "\t" + Place + "\t" + player1.getMoney() + "\t"
						+ player2.getMoney() + "\t" + player.getName() + " is in Free Parking");
			}
			// Property Check
			if (AL.get(Place).getStatus().equals(player.getName())) {
				writer.println(player.getName() + "\t" + Dice + "\t" + Place + "\t" + player1.getMoney() + "\t"
						+ player2.getMoney() + "\t" + player.getName() + " " + "has" + " " + AL.get(Place).getName());
			}
			if (AL.get(Place).getStatus().equals(other.getName())) {
				if (AL.get(Place) instanceof Land) {
					//

					player.setMoney(player.getMoney() - AL.get(Place).getRent());
					other.setMoney(other.getMoney() + AL.get(Place).getRent());
					if (player.getMoney() < 0) {
						nocash = true;
						player.setMoney(player.getMoney() + AL.get(Place).getRent());
						other.setMoney(other.getMoney() - AL.get(Place).getRent());

					} else {
						writer.println(player.getName() + "\t" + Dice + "\t" + Place + "\t" + player1.getMoney() + "\t"
								+ player2.getMoney() + "\t" + player.getName() + " paid rent for "
								+ AL.get(Place).getName());
					}

				}
				if (AL.get(Place) instanceof Company) {
					//
					player.setMoney(player.getMoney() - 4 * Dice);
					other.setMoney(other.getMoney() + 4 * Dice);
					if (player.getMoney() < 0) {
						nocash = true;
						player.setMoney(player.getMoney() + 4 * Dice);
						other.setMoney(other.getMoney() - 4 * Dice);
					} else {
						writer.println(player.getName() + "\t" + Dice + "\t" + Place + "\t" + player1.getMoney() + "\t"
								+ player2.getMoney() + "\t" + player.getName() + " paid rent for "
								+ AL.get(Place).getName());
					}

				}
				if (AL.get(Place) instanceof Rail) {

					player.setMoney(player.getMoney() - 25 * railcounter);
					other.setMoney(other.getMoney() + 25 * railcounter);
					if (player.getMoney() < 0) {
						nocash = true;
						player.setMoney(player.getMoney() + 25 * railcounter);
						other.setMoney(other.getMoney() - 25 * railcounter);
					} else {
						writer.println(player.getName() + "\t" + Dice + "\t" + Place + "\t" + player1.getMoney() + "\t"
								+ player2.getMoney() + "\t" + player.getName() + " paid rent for "
								+ AL.get(Place).getName());
					}

				}
			}
			if (AL.get(Place).getStatus().equals("Empty")) {

				if (AL.get(Place) instanceof Land || AL.get(Place) instanceof Rail
						|| AL.get(Place) instanceof Company) {
					AL.get(Place).setStatus(player.getName());
					player.setMoney(player.getMoney() - AL.get(Place).getCost());
					Bank.setMoney(Bank.getMoney() + AL.get(Place).getCost());
					if (player.getMoney() < 0) {
						nocash = true;
						AL.get(Place).setStatus("Empty");
						player.setMoney(player.getMoney() + AL.get(Place).getCost());
						Bank.setMoney(Bank.getMoney() - AL.get(Place).getCost());
					} else {
						writer.println(player.getName() + "\t" + Dice + "\t" + Place + "\t" + player1.getMoney() + "\t"
								+ player2.getMoney() + "\t" + player.getName() + " " + "bought" + " "
								+ AL.get(Place).getName());
					}

				}

			}

			if (AL.get(Place) instanceof Miss) {

				if (AL.get(Place).getName().equals("Go to Jail")) {
					player.setPos(11);
					if (player.getName().equals("Player 1")) {
						jailp1 = player.getName();
						jail1counter++;
					}
					if (player.getName().equals("Player 2")) {
						jailp2 = player.getName();
						jail2counter++;
					}

					writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t" + player1.getMoney()
							+ "\t" + player2.getMoney() + "\t" + player.getName() + " " + "went to jail");

				}

				if (AL.get(Place).getName().equals("Income Tax") || AL.get(Place).getName().equals("Super Tax")) {

					player.setMoney(player.getMoney() - 100);
					Bank.setMoney(Bank.getMoney() + 100);
					if (player.getMoney() < 0) {
						nocash = true;
						player.setMoney(player.getMoney() + 100);
						Bank.setMoney(Bank.getMoney() - 100);
					} else {
						writer.println(player.getName() + "\t" + Dice + "\t" + Place + "\t" + player1.getMoney() + "\t"
								+ player2.getMoney() + "\t" + player.getName() + " " + "paid Tax");
					}

				}
				if (AL.get(Place).getName().equals("Chance")) {
					String card = cardtype_chance.poll();

					cardtype_chance.add(card);

					if (card.equals("Advance to Go (Collect $200)")) {
						player.setPos(1);
						player.setMoney(player.getMoney() + 200);
						Bank.setMoney(Bank.getMoney() - 200);
						writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
								+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
								+ "draw" + " " + card);
					}
					if (card.equals("Advance to Leicester Square")) {

						player.setPos(27);

						if (AL.get(27).getStatus().equals(player.getName())) {
							writer.printf(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
									+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
									+ "draw" + " " + card + " ");
							writer.printf(player.getName() + " " + "has " + AL.get(27).getName() + "\r\n");
						}
						if (AL.get(27).getStatus().equals(other.getName())) {
							player.setMoney(player.getMoney() - AL.get(player.getPos()).getRent());
							other.setMoney(other.getMoney() + AL.get(player.getPos()).getRent());
							if (player.getMoney() < 0) {
								nocash = true;
								player.setMoney(player.getMoney() + AL.get(player.getPos()).getRent());
								other.setMoney(other.getMoney() - AL.get(player.getPos()).getRent());
							} else {
								writer.printf(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
										+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
										+ "draw" + " " + card);
								writer.printf(" " + player.getName() + " paid rent for Leicester Square\r\n");
							}

						}
						if (AL.get(27).getStatus().equals("Empty")) {
							AL.get(27).setStatus(player.getName());
							player.setMoney(player.getMoney() - AL.get(27).getCost());
							Bank.setMoney(Bank.getMoney() + AL.get(27).getCost());
							if (player.getMoney() < 0) {
								nocash = true;
								AL.get(27).setStatus("Empty");
								player.setMoney(player.getMoney() + AL.get(27).getCost());
								Bank.setMoney(Bank.getMoney() - AL.get(27).getCost());
							} else {
								writer.printf(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
										+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
										+ "draw" + " " + card + " ");
								writer.printf(player.getName() + " " + "bought " + AL.get(27).getName() + "\r\n");
							}

						}

					}
					if (card.equals("Pay poor tax of $15")) {
						player.setMoney(player.getMoney() - 15);
						Bank.setMoney(Bank.getMoney() + 15);
						if (player.getMoney() < 0) {
							nocash = true;
							player.setMoney(player.getMoney() + 15);
							Bank.setMoney(Bank.getMoney() - 15);
						} else {
							writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
									+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
									+ "draw" + " " + card);
						}

					}
					if (card.equals("Your building loan matures – collect $150")) {
						player.setMoney(player.getMoney() + 150);
						Bank.setMoney(Bank.getMoney() - 150);
						writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
								+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
								+ "draw" + " " + card);
					}
					if (card.equals("You have won a crossword competition - collect $100 ")) {
						player.setMoney(player.getMoney() + 100);
						Bank.setMoney(Bank.getMoney() - 100);
						writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
								+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
								+ "draw" + " " + card);
					}
					if (card.equals("Go back 3 spaces")) {
						player.setPos(Place - 3);
						if (player.getPos() == 5) {
							player.setMoney(player.getMoney() - 100);
							Bank.setMoney(Bank.getMoney() + 100);
							if (player.getMoney() < 0) {
								nocash = true;
								player.setMoney(player.getMoney() + 100);
								Bank.setMoney(Bank.getMoney() - 100);
							} else {
								writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
										+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
										+ "draw" + " " + card + " " + player.getName() + " " + "paid Tax");
							}
							if (player.getPos() == 20) {
								if (AL.get(20).getStatus().equals(player.getName())) {
									writer.printf(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
											+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName()
											+ " " + "draw" + " " + card + " ");
									writer.printf(player.getName() + " " + "has " + AL.get(20).getName() + "\r\n");
								}
								if (AL.get(20).getStatus().equals(other.getName())) {
									player.setMoney(player.getMoney() - AL.get(player.getPos()).getRent());
									other.setMoney(other.getMoney() + AL.get(player.getPos()).getRent());
									if (player.getMoney() < 0) {
										nocash = true;
										player.setMoney(player.getMoney() + AL.get(player.getPos()).getRent());
										other.setMoney(other.getMoney() - AL.get(player.getPos()).getRent());
									} else {
										writer.printf(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
												+ player1.getMoney() + "\t" + player2.getMoney() + "\t"
												+ player.getName() + " " + "draw" + " " + card + " ");
										writer.printf(
												" " + player.getName() + " paid rent for Leicester Square" + "\r\n");
									}

								}
								if (AL.get(20).getStatus().equals("Empty")) {
									AL.get(20).setStatus(player.getName());
									player.setMoney(player.getMoney() - AL.get(20).getCost());
									Bank.setMoney(Bank.getMoney() + AL.get(20).getCost());
									if (player.getMoney() < 0) {
										nocash = true;
										AL.get(20).setStatus("Empty");
										player.setMoney(player.getMoney() + AL.get(20).getCost());
										Bank.setMoney(Bank.getMoney() - AL.get(20).getCost());
									} else {
										writer.printf(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
												+ player1.getMoney() + "\t" + player2.getMoney() + "\t"
												+ player.getName() + " " + "draw" + " " + card + " ");
										writer.printf(
												player.getName() + " " + "bought " + AL.get(20).getName() + "\r\n");
									}
								}
							}
						}
						if (player.getPos() == 34) {
							String cardtemp = cardtype_community.poll();

							cardtype_community.add(cardtemp);
							if (cardtemp.equals("Advance to Go (Collect $200)")) {
								player.setPos(1);
								player.setMoney(player.getMoney() + 200);
								Bank.setMoney(Bank.getMoney() - 200);
								writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
										+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
										+ "draw" + " " + "Go back 3 spaces " + player.getName() + " " + cardtemp);
							}
							if (cardtemp.equals("Bank error in your favor – collect $75")) {

								player.setMoney(player.getMoney() + 75);
								Bank.setMoney(Bank.getMoney() - 75);
								writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
										+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
										+ "draw" + " " + "Go back 3 spaces " + player.getName() + " " + cardtemp);
							}
							if (cardtemp.equals("Doctor's fees – Pay $50")) {
								player.setMoney(player.getMoney() - 50);
								Bank.setMoney(Bank.getMoney() + 50);
								if (player.getMoney() < 0) {
									nocash = true;
									player.setMoney(player.getMoney() + 50);
									Bank.setMoney(Bank.getMoney() - 50);
								} else {
									writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
											+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName()
											+ " " + "draw" + " " + "Go back 3 spaces " + player.getName() + " "
											+ cardtemp);
								}

							}
							if (cardtemp.equals("It is your birthday Collect $10 from each player")) {
								player.setMoney(player.getMoney() + 10);
								other.setMoney(other.getMoney() - 10);
								writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
										+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
										+ "draw" + " " + "Go back 3 spaces " + player.getName() + " " + cardtemp);
							}
							if (cardtemp.equals(
									"Grand Opera Night – collect $50 from every player for opening night seats")) {
								player.setMoney(player.getMoney() + 50);
								other.setMoney(other.getMoney() - 50);
								writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
										+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
										+ "draw" + " " + "Go back 3 spaces " + player.getName() + " " + cardtemp);
							}
							if (cardtemp.equals("Income Tax refund – collect $20")) {
								player.setMoney(player.getMoney() + 20);
								Bank.setMoney(Bank.getMoney() - 20);
								writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
										+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
										+ "draw" + " " + "Go back 3 spaces " + player.getName() + " " + cardtemp);
							}
							if (cardtemp.equals("Life Insurance Matures – collect $100")) {
								player.setMoney(player.getMoney() + 100);
								Bank.setMoney(Bank.getMoney() - 100);
								writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
										+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
										+ "draw" + " " + "Go back 3 spaces " + player.getName() + " " + cardtemp);
							}
							if (cardtemp.equals("Pay Hospital Fees of $100")) {
								player.setMoney(player.getMoney() - 100);
								Bank.setMoney(Bank.getMoney() + 100);
								if (player.getMoney() < 0) {
									nocash = true;
									player.setMoney(player.getMoney() + 100);
									Bank.setMoney(Bank.getMoney() - 100);
								} else {
									writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
											+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName()
											+ " " + "draw" + " " + "Go back 3 spaces " + player.getName() + " "
											+ cardtemp);
								}
							}
							if (cardtemp.equals("Pay School Fees of $50")) {
								player.setMoney(player.getMoney() - 50);
								Bank.setMoney(Bank.getMoney() + 50);
								if (player.getMoney() < 0) {
									nocash = true;
									player.setMoney(player.getMoney() + 50);
									Bank.setMoney(Bank.getMoney() - 50);
								} else {
									writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
											+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName()
											+ " " + "draw" + " " + "Go back 3 spaces " + player.getName() + " "
											+ cardtemp);
								}
							}
							if (cardtemp.equals("You inherit $100")) {
								player.setMoney(player.getMoney() + 100);
								Bank.setMoney(Bank.getMoney() - 100);
								writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
										+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
										+ "draw" + " " + "Go back 3 spaces " + player.getName() + " " + cardtemp);
							}
							if (cardtemp.equals("From sale of stock you get $50")) {
								player.setMoney(player.getMoney() + 50);
								Bank.setMoney(Bank.getMoney() - 50);
								writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
										+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
										+ "draw" + " " + "Go back 3 spaces " + player.getName() + " " + cardtemp);
							}
						}

					}
				}
				if (AL.get(Place).getName().equals("Community Chest")) {

					String card = cardtype_community.poll();
					cardtype_community.add(card);

					if (card.equals("Advance to Go (Collect $200)")) {
						player.setPos(1);
						player.setMoney(player.getMoney() + 200);
						Bank.setMoney(Bank.getMoney() - 200);
						writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
								+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
								+ "draw" + " " + card);
					}
					if (card.equals("Bank error in your favor – collect $75")) {

						player.setMoney(player.getMoney() + 75);
						Bank.setMoney(Bank.getMoney() - 75);
						writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
								+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
								+ "draw" + " " + card);
					}
					if (card.equals("Doctor's fees – Pay $50")) {
						player.setMoney(player.getMoney() - 50);
						Bank.setMoney(Bank.getMoney() + 50);
						if (player.getMoney() < 0) {
							nocash = true;
							player.setMoney(player.getMoney() + 50);
							Bank.setMoney(Bank.getMoney() - 50);
						} else {
							writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
									+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
									+ "draw" + " " + card);
						}

					}
					if (card.equals("It is your birthday Collect $10 from each player")) {
						player.setMoney(player.getMoney() + 10);
						other.setMoney(other.getMoney() - 10);
						writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
								+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
								+ "draw" + " " + card);
					}
					if (card.equals("Grand Opera Night – collect $50 from every player for opening night seats")) {
						player.setMoney(player.getMoney() + 50);
						other.setMoney(other.getMoney() - 50);
						writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
								+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
								+ "draw" + " " + card);
					}
					if (card.equals("Income Tax refund – collect $20")) {
						player.setMoney(player.getMoney() + 20);
						Bank.setMoney(Bank.getMoney() - 20);
						writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
								+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
								+ "draw" + " " + card);
					}
					if (card.equals("Life Insurance Matures – collect $100")) {
						player.setMoney(player.getMoney() + 100);
						Bank.setMoney(Bank.getMoney() - 100);
						writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
								+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
								+ "draw" + " " + card);
					}
					if (card.equals("Pay Hospital Fees of $100")) {
						player.setMoney(player.getMoney() - 100);
						Bank.setMoney(Bank.getMoney() + 100);
						if (player.getMoney() < 0) {
							nocash = true;
							player.setMoney(player.getMoney() + 100);
							Bank.setMoney(Bank.getMoney() - 100);
						} else {
							writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
									+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
									+ "draw" + " " + card);
						}

					}
					if (card.equals("Pay School Fees of $50")) {
						player.setMoney(player.getMoney() - 50);
						Bank.setMoney(Bank.getMoney() + 50);
						if (player.getMoney() < 0) {
							nocash = true;
							player.setMoney(player.getMoney() + 50);
							Bank.setMoney(Bank.getMoney() - 50);
						} else {
							writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
									+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
									+ "draw" + " " + card);
						}
					}
					if (card.equals("You inherit $100")) {
						player.setMoney(player.getMoney() + 100);
						Bank.setMoney(Bank.getMoney() - 100);
						writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
								+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
								+ "draw" + " " + card);
					}
					if (card.equals("From sale of stock you get $50")) {
						player.setMoney(player.getMoney() + 50);
						Bank.setMoney(Bank.getMoney() - 50);
						writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t"
								+ player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + " "
								+ "draw" + " " + card);
					}
				}
			}
		}
		if (cont == 5) {
			cont = 3;
		}
		if (nocash == true) {
			writer.println(player.getName() + "\t" + Dice + "\t" + player.getPos() + "\t" + player1.getMoney() + "\t"
					+ player2.getMoney() + "\t" + player.getName() + " goes bankrupt");
			return 1;
		}
		return 0;
	}

}
