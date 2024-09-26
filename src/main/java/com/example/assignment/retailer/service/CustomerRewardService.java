package com.example.assignment.retailer.service;

import java.util.List;

import com.example.assignment.retailer.model.Customer;
import com.example.assignment.retailer.model.CustomerReponse;

public interface CustomerRewardService {

	public Customer getCustomerTranscations(String customerName);

	public List<CustomerReponse> calculateCustomerRewardPoints();

	public String addData();
}
