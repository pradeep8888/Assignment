package com.example.assignment.retailer.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import jakarta.persistence.Id;


public class Customer {

	private String customerName;
	
	private Map<String,Integer> monthlyPoints = new HashMap<>();

	public Customer(String customerName, Map<String, Integer> monthlyPoints) {
		super();
		this.customerName = customerName;
		this.monthlyPoints = monthlyPoints;
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
