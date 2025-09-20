package com.vatManager.vatManager.enums;

public enum SortBy {


	
	FOOM("foom"),
	ID("id"),
	DATE("date"),
	BILLNUMBER("billNumber"),
	PANNUMBER("panNumber"),
	AMOUNT("amount"),
	VATTAX("vatTax"),
	TOTAL("total");
	
	
	private final String columnName;
	
	 SortBy(String columnName) {
		this.columnName = columnName;
	}
	 
	 
	 public String getColumnName() {
		 return this.columnName;
	 }
	
}
