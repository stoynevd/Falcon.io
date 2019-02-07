package com.Models;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Purchase {

	private static int purchase_id = 7;
	
	private static int id;

	private int product_id;

	private int user_id;

	private double price;

	private String date;

	public Purchase(int id, int product_id, int user_id, double price, String date) {
		super();
		id = ++purchase_id;
		this.product_id = product_id;
		this.user_id = user_id;
		this.price = price;
		this.date = date;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public static int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
