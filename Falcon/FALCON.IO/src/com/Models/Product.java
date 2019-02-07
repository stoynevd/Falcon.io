package com.Models;
import java.util.concurrent.atomic.AtomicInteger;

public class Product {

	private static int product_id = 10;

	private String catalog_id;

	private String title;

	private String author;

	private double price;

//	public static enum type {
//		Book, eBook, BoardGame
//	};

	private String type;

	private int quantity;

	private int min_players = 0;

	private int max_players = 0;

	private boolean status;

	public Product(int id, String catalog_id, String title, String author, double price, String type, int quantity,
			int min_players, int max_players, boolean status) {
		id = ++product_id;
		this.catalog_id = catalog_id;
		this.title = title;
		this.author = author;
		this.price = price;
		this.type = type;
		this.quantity = quantity;
		this.min_players = min_players;
		this.max_players = max_players;
		this.status = status;
	}

	public static int getId() {
		return product_id;
	}

	public String getCatalog_id() {
		return catalog_id;
	}

	public void setCatalog_id(String catalog_id) {
		this.catalog_id = catalog_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getMin_players() {
		return min_players;
	}

	public void setMin_players(int min_players) {
		this.min_players = min_players;
	}

	public int getMax_players() {
		return max_players;
	}

	public void setMax_players(int max_players) {
		this.max_players = max_players;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
