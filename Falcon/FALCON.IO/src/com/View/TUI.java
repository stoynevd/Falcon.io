package com.View;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import com.Database.DatabaseHelper;
import com.Models.Product;
import com.Models.Purchase;
import com.Models.Request;
import com.Models.User;

public class TUI {

	Scanner scanner = new Scanner(System.in);

	DatabaseHelper db = new DatabaseHelper();

	public void startUI() {

		ArrayList<String> options = new ArrayList<>();

		String userInput = "";

		options.add("Register");
		options.add("Log in");
		options.add("Exit");

		boolean isValid = false;

		System.out.println("Welcome to Falcon.io bookstore\n 1. Register\n 2. Log in\n 3. Exit\n");

		do {

			userInput = scanner.nextLine();

			if (options.contains(userInput)) {

				if (userInput.equals("Register")) {

					isValid = true;

					ArrayList<String> userDetails = printRegistrationForm();

					User newUser = new User(Integer.parseInt(userDetails.get(0)), userDetails.get(1),
							userDetails.get(2), userDetails.get(3), Double.parseDouble(userDetails.get(4)));

					db.addUser(newUser);

					printDashboard(newUser.getName());

				} else if (userInput.equals("Log in")) {

					isValid = true;

					printLogInForm();

				} else {

					System.out.println("Bye bye!");

					break;

				}

			} else {

				isValid = false;

				System.out.println("Invalid command, please try again");

			}

		} while (!(isValid));

	}

	private ArrayList<String> printRegistrationForm() {

		ArrayList<String> userDetails = new ArrayList<String>();

		String userInput = "";

		db.getProductByCatlogId(userInput);
		userDetails.add(Integer.toString(Product.getId()));
		userDetails.add("User");

		System.out.println(
				"Please enter your username. (It should start with a capital letter and contain no special symbols)");

		userInput = scanner.next();

		while (!userInput.matches("^[A-Z0-9][a-z0-9_-]+[a-zA-Z0-9](?<![_\\s\\-])$")) {

			System.out.println("Invalid username, please try again");

			userInput = scanner.next();

		}

		userDetails.add(userInput);

		System.out.println(
				"Enter password (The password's first character must be a letter, it must contain at least 4 characters and no more than 15 characters and no characters other than letters, numbers and the underscore may be used)");

		userInput = scanner.next();

		while (!userInput.matches("^[a-zA-Z]\\w{3,14}$")) {

			userInput = scanner.next();

		}

		userDetails.add(userInput);
		userDetails.add("0.0");

		return userDetails;

	}

	private void printLogInForm() {

		String username = "";

		String password = "";

		System.out.println("Please enter your username.");

		username = scanner.next();

		System.out.println("Please enter your password.");

		password = scanner.next();

		try {

			if (!db.getUser(username).equals(null) && db.getUser(username).getPassword().equals(password)) {

				if (db.getUser(username).getRank().equals("Admin")) {

					printAdminDashboard();

				} else {

					printDashboard(username);

				}
			}

		} catch (Exception e) {

			e.printStackTrace();

			System.out.println("Invalid username or password");

			printLogInForm();

		}

	}

	private void searchAProductByTitle() {

		System.out.println("Please enter the title of the book(or part of it)");

		String userInput = scanner.nextLine();

		for (int i = 0; i < db.getAllProducts().size(); i++) {

			if (db.getAllProducts().get(i).getTitle().contains(userInput)) {

				System.out.println("Catalog number: " + db.getAllProducts().get(i).getCatalog_id() + "\nBook author: "
						+ db.getAllProducts().get(i).getAuthor() + "\nBook title: "
						+ db.getAllProducts().get(i).getTitle() + "\nBook price: "
						+ db.getAllProducts().get(i).getPrice() + "\n");

			} else if (db.getAllProducts().get(i).getTitle().equals(null)) {

				System.out.println("Book not found");

				searchAProductByTitle();

			}

		}

	}

	private void searchAProductByAuthor() {

		System.out.println("Please enter the author of the book(or part of it)");

		String userInput = scanner.nextLine();

		for (int i = 0; i < db.getAllProducts().size(); i++) {

			if (db.getAllProducts().get(i).getAuthor().contains(userInput)) {

				System.out.println("Catalog number: " + db.getAllProducts().get(i).getCatalog_id() + "\nBook author: "
						+ db.getAllProducts().get(i).getAuthor() + "\nBook title: "
						+ db.getAllProducts().get(i).getTitle() + "\nBook price: "
						+ db.getAllProducts().get(i).getPrice() + "\n");

			} else if (db.getAllProducts().get(i).getAuthor().equals(null)) {

				System.out.println("Book not found");

				searchAProductByAuthor();

			}

		}

	}

	private void printDashboard(String username) {

		boolean isValid = false;

		String answer = "";

		System.out.println("\n" + username
				+ "'s Dashboard\n 1. Search a book by it's title\n 2. Search a book by author\n 3. Search a board game by minimum number of players\n 4. Product catalog\n");

		do {

			String userInput = scanner.nextLine();

			if (userInput.equals("1.") || userInput.equals("Search a book by it's title")) {

				searchAProductByTitle();

				System.out.println(
						"To purchase the book write its catalog id.\nIf you want to continue searching type search");

				userInput = scanner.nextLine();

				if (userInput.equals("search")) {

					printDashboard(username);

				} else if (db.getProductByCatlogId(userInput) != null
						&& db.getProductByCatlogId(userInput).getQuantity() > 0) {

					if (db.getUser(username).getBalance() > db.getProductByCatlogId(userInput).getPrice()) {

						db.addPurchase(new Purchase(new AtomicInteger().incrementAndGet(), Product.getId(),
								db.getUser(username).getId(), db.getProductByCatlogId(userInput).getPrice(),
								new Date().toString()));

						db.updateBalance(db.getUser(username), db.getProductByCatlogId(userInput));

						System.out.println(
								"You have successfully purchased the specified book. Would you like to continue searching?");

					}

				} else {

					System.out.println("Unknown command\n");

					printDashboard(username);

				}

				isValid = true;

			} else if (userInput.equals("2.") || userInput.equals("Search a book by it's author")) {

				searchAProductByAuthor();

				System.out.println(
						"To purchase the book write its catalog id.\nIf you want to continue searching type search");

				userInput = scanner.nextLine();

				if (userInput.equals("search")) {

					printDashboard(username);

				} else if (db.getProductByCatlogId(userInput) != null
						&& db.getProductByCatlogId(userInput).getQuantity() > 0) {

					if (db.getUser(username).getBalance() > db.getProductByCatlogId(userInput).getPrice()) {

						db.addPurchase(new Purchase(Purchase.getId(), Product.getId(), db.getUser(username).getId(),
								db.getProductByCatlogId(userInput).getPrice(), new Date().toString()));

						db.updateBalance(db.getUser(username), db.getProductByCatlogId(userInput));

						db.updateQuantity(db.getProductByCatlogId(userInput));

					}

				} else if (db.getProductByCatlogId(userInput) != null
						&& db.getProductByCatlogId(userInput).getQuantity() == 0) {

					System.out.println(
							"Sorry the selected product is out of stock. Would you like to request it? (yes/no)");

					answer = scanner.nextLine();

					if (answer.equals("yes") || answer.equals("Yes")) {

						db.addRequest(new Request(Request.getId(), db.getUser(username).getId(), Product.getId(),
								db.getProductByCatlogId(userInput).getQuantity(), new Date().toString()));

						System.out.println("Request for item successfully added.\n");

						printDashboard(username);

					} else {

						printDashboard(username);

					}

				} else {

					System.out.println("Unknown command\n");

					printDashboard(username);

				}

				isValid = true;

			} else if (userInput.equals("3.")
					|| userInput.equals("Search a board game by the minimum number of players")) {

				searchGameByMinNumberOfPlayers();

				System.out.println(
						"To purchase the book write its catalog id.\nIf you want to continue searching type search");

				userInput = scanner.nextLine();

				if (userInput.equals("search")) {

					printDashboard(username);

				} else if (db.getProductByCatlogId(userInput) != null
						&& db.getProductByCatlogId(userInput).getQuantity() > 0) {

					if (db.getUser(username).getBalance() > db.getProductByCatlogId(userInput).getPrice()) {

						db.addPurchase(new Purchase(Purchase.getId(), Product.getId(), db.getUser(username).getId(),
								db.getProductByCatlogId(userInput).getPrice(), new Date().toString()));

						db.updateBalance(db.getUser(username), db.getProductByCatlogId(userInput));

						db.updateQuantity(db.getProductByCatlogId(userInput));

					}

				} else if (db.getProductByCatlogId(userInput) != null
						&& db.getProductByCatlogId(userInput).getQuantity() == 0) {

					System.out.println(
							"Sorry the selected product is out of stock. Would you like to request it? (yes/no)");

					answer = scanner.nextLine();

					if (answer.equals("yes") || answer.equals("Yes")) {

						db.addRequest(new Request(Request.getId(), db.getUser(username).getId(), Product.getId(),
								db.getProductByCatlogId(userInput).getQuantity(), new Date().toString()));

						System.out.println("Request for item successfully added.\n");

						printDashboard(username);

					} else {

						printDashboard(username);

					}

				} else {

					System.out.println("Unknown command\n");

					printDashboard(username);

				}

			} else if (userInput.equals("4.") || userInput.equals("Product catalog")) {

				for (int i = 0; i < db.getAllProducts().size(); i++) {

					System.out.println("Catalog number: " + db.getAllProducts().get(i).getCatalog_id()
							+ "\nBook author: " + db.getAllProducts().get(i).getAuthor() + "\nBook title: "
							+ db.getAllProducts().get(i).getTitle() + "\nBook price: "
							+ db.getAllProducts().get(i).getPrice() + "\n");

				}

				System.out.println(
						"To purchase the book write its catalog id.\nIf you want to continue searching type search");

				userInput = scanner.nextLine();

				if (userInput.equals("search")) {

					printDashboard(username);

				} else if (db.getProductByCatlogId(userInput) != null
						&& db.getProductByCatlogId(userInput).getQuantity() > 0) {

					if (db.getUser(username).getBalance() > db.getProductByCatlogId(userInput).getPrice()) {

						db.addPurchase(new Purchase(Purchase.getId(), Product.getId(), db.getUser(username).getId(),
								db.getProductByCatlogId(userInput).getPrice(), new Date().toString()));

						db.updateBalance(db.getUser(username), db.getProductByCatlogId(userInput));

						db.updateQuantity(db.getProductByCatlogId(userInput));

					}

				} else if (db.getProductByCatlogId(userInput) != null
						&& db.getProductByCatlogId(userInput).getQuantity() == 0) {

					System.out.println(
							"Sorry the selected product is out of stock. Would you like to request it? (yes/no)");

					answer = scanner.nextLine();

					if (answer.equals("yes") || answer.equals("Yes")) {

						System.out.println("What quantity would you like to order?");

						answer = scanner.nextLine();

						db.addRequest(new Request(Request.getRequest_id(), db.getUser(username).getId(),
								Product.getId(), Integer.parseInt(answer), new Date().toString()));

						System.out.println("Request for item successfully added.\n");

						printDashboard(username);

					} else {

						printDashboard(username);

					}

				} else {

					System.out.println("Unknown command\n");

					printDashboard(username);

				}

			}

		} while (!(isValid));
	}

	private void printAdminDashboard() {

		boolean isValid = false;

		String username = "admin";

		System.out.println("\n" + username + "'s Dashboard\n 1. Add a new book\n 2. Add a new board game");

		do {

			String userInput = scanner.nextLine();

			if (userInput.equals("1.") || userInput.equals("Add a new book")) {

				ArrayList<String> bookdetails = new ArrayList<String>();

				System.out.println("Enter the catalog id of the book");

				userInput = scanner.nextLine();

				bookdetails.add(userInput);

				System.out.println("Enter the title of the book");

				userInput = scanner.nextLine();

				bookdetails.add(userInput);

				System.out.println("Enter the author of the book");

				userInput = scanner.nextLine();

				bookdetails.add(userInput);

				System.out.println("Enter the price of the book");

				userInput = scanner.nextLine();

				bookdetails.add(userInput);

				bookdetails.add("Book");

				System.out.println("Enter the quantity of the book");

				userInput = scanner.nextLine();

				bookdetails.add(userInput);

				db.addProduct(new Product(Product.getId(), bookdetails.get(0), bookdetails.get(1), bookdetails.get(2),
						Double.parseDouble(bookdetails.get(3)), bookdetails.get(4),
						Integer.parseInt(bookdetails.get(5)), 0, 0, true));

				isValid = true;

			} else if (userInput.equals("2.") || userInput.equals("Add a new board game")) {

				ArrayList<String> boardGame = new ArrayList<String>();

				System.out.println("Enter the catalog id of the book");

				userInput = scanner.nextLine();

				boardGame.add(userInput);

				System.out.println("Enter the title of the book");

				userInput = scanner.nextLine();

				boardGame.add(userInput);

				System.out.println("Enter the author of the book");

				userInput = scanner.nextLine();

				boardGame.add(userInput);

				System.out.println("Enter the price of the book");

				userInput = scanner.nextLine();

				boardGame.add(userInput);

				boardGame.add("Book");

				System.out.println("Enter the quantity of the book");

				userInput = scanner.nextLine();

				boardGame.add(userInput);

				System.out.println("Enter the minimum number of players");

				userInput = scanner.nextLine();

				boardGame.add(userInput);

				System.out.println("Enter the maximum number of players");

				userInput = scanner.nextLine();

				boardGame.add(userInput);

				db.addProduct(new Product(Product.getId(), boardGame.get(0), boardGame.get(1), boardGame.get(2),
						Double.parseDouble(boardGame.get(3)), boardGame.get(4), Integer.parseInt(boardGame.get(4)),
						Integer.parseInt(boardGame.get(5)), Integer.parseInt(boardGame.get(6)), true));

				isValid = true;

			}

		} while (!(isValid));

	}

	private void searchGameByMinNumberOfPlayers() {

		System.out.println("Please enter the minimum number of players");

		String userInput = scanner.nextLine();

		for (int i = 0; i < db.getAllProducts().size(); i++) {

			if (db.getAllProducts().get(i).getMin_players() == Integer.parseInt(userInput)) {

				System.out.println("Catalog number: " + db.getAllProducts().get(i).getCatalog_id()
						+ "\nBoard game author: " + db.getAllProducts().get(i).getAuthor() + "\nBoard game title: "
						+ db.getAllProducts().get(i).getTitle() + "\nBoard game price: "
						+ db.getAllProducts().get(i).getPrice() + "\nMaximum number of players: "
						+ db.getAllProducts().get(i).getMax_players());

			} else if (db.getAllProducts().get(i).getMin_players() == 0) {

				System.out.println("Board game not found");

				searchGameByMinNumberOfPlayers();

			}

		}

	}

}
