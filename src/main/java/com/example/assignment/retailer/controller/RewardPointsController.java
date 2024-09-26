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
	private TranscationRepository transcationrepository;

	@Autowired
	private CustomerRewardService customerRewardService;

	@PostMapping("/addData")
	public ResponseEntity<String> insertThreeMonthAmountInDB() {
		try {
			transcationrepository.save(new Transcations(14l, "PraveenK", LocalDate.of(2024, 7, 5), 70));
			transcationrepository.save(new Transcations(14l, "PraveenK", LocalDate.of(2024, 6, 25), 70));
			transcationrepository.save(new Transcations(13l, "AvinashM", LocalDate.of(2024, 5, 20), 100));
			transcationrepository.save(new Transcations(11l, "PradeepA", LocalDate.of(2024, 5, 15), 120));
			transcationrepository.save(new Transcations(12l, "PrashantJ", LocalDate.of(2024, 6, 10), 159));
			transcationrepository.save(new Transcations(13l, "AvinashM", LocalDate.of(2024, 5, 25), 130));
			transcationrepository.save(new Transcations(14l, "PraveenK", LocalDate.of(2024, 5, 15), 120));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return ResponseEntity.badRequest().body("Unable to add Data");
		}
		return ResponseEntity.ok("Successfuly Added Data");
	}

	@GetMapping("/getCustomer/{name}")
	public ResponseEntity<Customer> getCustomerTranscations(@PathVariable String name) {

		Customer customer = customerRewardService.getCustomerTranscations(name);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	@GetMapping("/getAllCustomer")
	public ResponseEntity<List<CustomerReponse>> getAllCustomer() {
		List<CustomerReponse> allCustomer = customerRewardService.calculateCustomerRewardPoints();

		return ResponseEntity.ok(allCustomer);
	}

}
