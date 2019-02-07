package com.Database;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.Models.Product;
import com.Models.Purchase;
import com.Models.Request;
import com.Models.User;

public class DatabaseHelper {

	private Driver driver = new Driver();

	public void addProduct(Product newProduct) {

		try {

			Statement stm = driver.CONN();

			String query = " INSERT INTO products VALUES( " + newProduct.getId() + ", " + "'"
					+ newProduct.getCatalog_id() + "', " + "'" + newProduct.getTitle() + "', " + "'"
					+ newProduct.getAuthor() + "', " + newProduct.getPrice() + ", " + "'" + newProduct.getType() + "', "
					+ newProduct.getQuantity() + ", " + newProduct.getMin_players() + ", " + newProduct.getMax_players()
					+ ", " + newProduct.isStatus() + " )";

			System.out.println(query);

			stm.execute(query);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public Product getProduct(int id) {

		try {

			Statement stm = driver.CONN();

			String query = "SELECT * FROM products WHERE products.id = " + id;

			ResultSet rs = stm.executeQuery(query);

			while (rs.next()) {

				return new Product(id, rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5),
						rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getBoolean(10));

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}

	public Product getProductByCatlogId(String catlogId) {

		try {

			Statement stm = driver.CONN();

			String query = "SELECT * FROM products WHERE products.catalogId = '" + catlogId + "'";

			ResultSet rs = stm.executeQuery(query);

			while (rs.next()) {

				return new Product(rs.getInt(1), catlogId, rs.getString(3), rs.getString(4), rs.getDouble(5),
						rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getBoolean(10));

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<Product> getAllProducts() {

		ArrayList<Product> products = new ArrayList<>();

		try {

			Statement stm = driver.CONN();

			String query = "SELECT * FROM products";

			ResultSet rs = stm.executeQuery(query);

			while (rs.next()) {

				products.add(new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getDouble(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getBoolean(10)));

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return products;

	}

	public void deleteProduct(Product product) {

		try {

			Statement stm = driver.CONN();

			String query = "DELETE FROM products WHERE products.id = " + product.getId();

			stm.execute(query);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void addUser(User user) {

		try {

			Statement stm = driver.CONN();

			String query = " INSERT INTO users VALUES(" + user.getId() + ", '" + user.getRank() + "', '"
					+ user.getName() + "', '" + user.getPassword() + "', '" + user.getBalance() + "')";

			stm.execute(query);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public User getUser(String username) {

		try {

			Statement stm = driver.CONN();

			String query = "SELECT * FROM users WHERE name = " + "'" + username + "'";

			ResultSet rs = stm.executeQuery(query);

			while (rs.next()) {

				return new User(rs.getInt(1), username, rs.getString(3), rs.getString(4), rs.getDouble(5));

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	public ArrayList<User> getAllUsers() {

		ArrayList<User> users = new ArrayList<>();

		try {

			Statement stm = driver.CONN();

			String query = "SELECT * FROM users";

			ResultSet rs = stm.executeQuery(query);

			while (rs.next()) {

				users.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5)));

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return users;

	}

	public void addPurchase(Purchase purchase) {

		try {

			Statement stm = driver.CONN();

			String query = " INSERT INTO users_purchases VALUES(" + purchase.getId() + ", " + purchase.getUser_id()
					+ ", " + purchase.getProduct_id() + ", " + purchase.getPrice() + ", '" + purchase.getDate() + "')";

			stm.execute(query);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public void deletePurchase(Purchase purchase) {

		try {

			Statement stm = driver.CONN();

			String query = "DELETE FROM users_purchase WHERE users_purchases.id = " + purchase.getId();

			stm.execute(query);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public ArrayList<Purchase> getAllPurchases() {

		ArrayList<Purchase> purchases = new ArrayList<>();

		try {

			Statement stm = driver.CONN();

			String query = "SELECT * FROM users_purchases";

			ResultSet rs = stm.executeQuery(query);

			while (rs.next()) {

				purchases.add(new Purchase(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDouble(4), rs.getString(5)));

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return purchases;

	}

	public void updateBalance(User user, Product product) {

		try {

			Statement stm = driver.CONN();

			String query = "UPDATE users SET balance = " + (user.getBalance() - product.getPrice())
					+ "WHERE users.id = " + user.getId();

			stm.execute(query);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public void updateQuantity(Product product) {

		try {

			Statement stm = driver.CONN();

			String query = "UPDATE products SET quantity = quantity - 1 WHERE products.quantity > 0";

			stm.execute(query);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public void addRequest(Request request) {

		try {

			Statement stm = driver.CONN();

			String query = " INSERT INTO users_requests VALUES (" + request.getRequest_id() + ", "
					+ request.getUser_id() + ", " + request.getProduct_id() + ", " + request.getQuantity() + ", '"
					+ request.getDate() + "' );";

			stm.execute(query);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public void createProductsTable() {

		try {

			Statement stm = driver.CONN();

			String query = " CREATE TABLE IF NOT EXISTS  products ( id INT PRIMARY KEY, " + "catalogId varchar(50), "
					+ "title varchar(255), " + "author varchar(255), " + "price double, " + "type varchar(50), "
					+ "quantity int, " + "minPlayers int, " + "maxPlayers int, " + "active tinyint)";

			stm.execute(query);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public void createUsersTable() {

		try {

			Statement stm = driver.CONN();

			String query = " CREATE TABLE IF NOT EXISTS users ( id INT PRIMARY KEY, " + "rank varchar(255), " + "name varchar(255), "
					+ "password varchar(255), " + "balance double)";

			stm.execute(query);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public void createUsersPurchasesTable() {

		try {

			Statement stm = driver.CONN();

			String query = " CREATE TABLE IF NOT EXISTS users_purchases ( id INT PRIMARY KEY, " + "user_id int, " + "product_id int, "
					+ "price double, " + "date varchar(255))";

			stm.execute(query);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public void createUsersRequestsTable() {

		try {

			Statement stm = driver.CONN();

			String query = " CREATE TABLE IF NOT EXISTS users_requests ( id INT PRIMARY KEY, " + "user_id int, " + "product_id int, "
					+ "quantity int, " + "date varchar(255))";

			stm.execute(query);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}
