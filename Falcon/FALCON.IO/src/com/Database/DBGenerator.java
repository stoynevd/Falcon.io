package com.Database;

public class DBGenerator {
	
	public void generateDB() {
		
		DatabaseHelper db = new DatabaseHelper();
		
		db.createProductsTable();
		
		db.createUsersPurchasesTable();
		
		db.createUsersTable();
		
		db.createUsersRequestsTable();
		
	}

}
