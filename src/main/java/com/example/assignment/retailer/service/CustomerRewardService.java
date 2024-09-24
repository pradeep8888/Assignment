package com.example.assignment.retailer.service;

import java.util.List;
import java.util.Optional;

import com.example.assignment.retailer.entity.Transcations;
import com.example.assignment.retailer.model.Customer;
import com.example.assignment.retailer.model.CustomerReponse;

public interface CustomerRewardService {

	public List<Transcations> getAllCustomer();

	public Customer getCustomerTranscations(String customerName);

	public List<CustomerReponse> calculateCustomerRewardPoints();
	}
