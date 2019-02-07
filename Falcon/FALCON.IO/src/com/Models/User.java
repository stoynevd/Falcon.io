package com.Models;

public class User {

	private int id1;

	private static int user_id = 4;

	private String rank;

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	private String name;

	private String password;

	private double balance;

	public User(int id, String rank, String name, String password, double balance) {
		super();
		id1 = ++user_id;
		id = id1;
		this.rank = rank;
		this.name = name;
		this.password = password;
		this.balance = balance;
	}

	public int getId() {
		return id1;
	}

	public void setId(int id) {
		this.id1 = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

}
