package com.example.assignment.retailer.model;

import java.util.HashMap;
import java.util.Map;

/*
 * Data model for All Customer response in calculated monthly earned points
 * */
public class CustomerReponse {

	private String customerName;
	private Map<String, Integer> monthlyPoints = new HashMap<>();
	private int total;

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

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public CustomerReponse() {
		super();
	}

	public CustomerReponse(String customerName, Map<String, Integer> monthlyPoints, int total) {
		super();
		this.customerName = customerName;
		this.monthlyPoints = monthlyPoints;
		this.total = total;
	}

	public CustomerReponse(String customerName) {
		super();
		this.customerName = customerName;
	}

}
