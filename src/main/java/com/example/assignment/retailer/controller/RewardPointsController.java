package com.example.assignment.retailer.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.assignment.retailer.entity.Transcations;
import com.example.assignment.retailer.model.Customer;
import com.example.assignment.retailer.model.CustomerReponse;
import com.example.assignment.retailer.repository.TranscationRepository;
import com.example.assignment.retailer.service.CustomerRewardService;

@RestController
public class RewardPointsController {


	@Autowired
	private CustomerRewardService customerRewardService;

	/* 
	 * This api is adding three month data transactions
	 * */ 
	@PostMapping("/addData")
	public ResponseEntity<String> insertThreeMonthAmountInDB() {
		String response = null;
		try {
			
			response = customerRewardService.addData();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	/*
	 * In this Get API we are calling specific customer and there calculated points on Monthly basis 
	 * for the name of argument we need to pass then we can get info about customer
	 * 
	 *  */
	@GetMapping("/getCustomer/{name}")
	public ResponseEntity<Customer> getCustomerTranscations(@PathVariable String name) {

		Customer customer = customerRewardService.getCustomerTranscations(name);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	/*
	 * In This Get API we are fetching all customer information about customer with calculated rewards points earned
	 * from retailer and it will fetch all customer with his month basis calculated reward points
	 * */
	@GetMapping("/getAllCustomer")
	public ResponseEntity<List<CustomerReponse>> getAllCustomer() {
		List<CustomerReponse> allCustomer = customerRewardService.calculateCustomerRewardPoints();

		return ResponseEntity.ok(allCustomer);
	}

}
