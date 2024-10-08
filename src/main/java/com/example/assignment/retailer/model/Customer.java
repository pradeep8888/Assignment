package com.example.assignment.retailer.model;

import java.util.HashMap;
import java.util.Map;
/*
 * Data model for customer for specific customer response
 * */
public class Customer {

	private String customerName;

	private Map<String, Integer> monthlyPoints = new HashMap<>();

	public Customer(String customerName, Map<String, Integer> monthlyPoints) {
		super();
		this.customerName = customerName;
		this.monthlyPoints = monthlyPoints;
	}

	public Customer() {
		super();
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Map<String, Integer> getMonthlyPoints() {
		return monthlyPoints;
	}

	public void setMonthlyPoints(Map<String, Integer> monthlyPoints) {
		this.monthlyPoints = monthlyPoints;
	}

}
