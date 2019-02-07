package com.Models;

public class Request {

	private static int id = 0;
	
	private static int request_id;

	private int user_id;

	private int product_id;

	private int quantity;

	private String date;

	public Request(int request_id, int user_id, int product_id, int quantity, String date) {
		super();
		setRequest_id(++id);
		this.user_id = user_id;
		this.product_id = product_id;
		this.quantity = quantity;
		this.date = date;
	}

	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		Request.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public static int getRequest_id() {
		return request_id;
	}

	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}

}
