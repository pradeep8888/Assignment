/*
 * This is customerRewardService implementation class used to implement 
 * all methods which contains method logic calculate earned points based on common and specific  
 * */
package com.example.assignment.retailer.service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.retailer.entity.Transcations;
import com.example.assignment.retailer.model.Customer;
import com.example.assignment.retailer.model.CustomerReponse;
import com.example.assignment.retailer.repository.TranscationRepository;
/**
 * The class CustomerRewardServiceImpl
 * @author pradeep.andhare
 *
 */
@Service
public class CustomerRewardServiceImpl implements CustomerRewardService {

	@Autowired
	private TranscationRepository transcationrepository;

	@Override
	public String addData() {
		List<Transcations> asList = Arrays.asList(new Transcations(14l, "PraveenK", LocalDate.of(2024, 7, 5), 70),
				new Transcations(14l, "PraveenK", LocalDate.of(2024, 6, 25), 70),
				new Transcations(11l, "PradeepA", LocalDate.of(2024, 5, 15), 120),
				new Transcations(12l, "PrashantJ", LocalDate.of(2024, 6, 10), 159),
				new Transcations(13l, "AvinashM", LocalDate.of(2024, 5, 25), 130),
				new Transcations(14l, "PraveenK", LocalDate.of(2024, 5, 15), 120));
		List<Transcations> saveAll = null;
		try {
			saveAll = transcationrepository.saveAll(asList);
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "Unable to add data";
		}

		return "Successfully added Data";	
	}

	/*
	 * Here we are calculating specific customer which we want to check for that
	 * pass argument as name of Customer. Earned reward points of monthly basis
	 */
	@Override
	public Customer getCustomerTranscations(String customerName) {
		int sum = 0;
		Customer response = new Customer();
		Map<String, Integer> customerMap = new HashMap<>();
		try {
			if (customerName.isBlank() || customerName == null) {
				throw new NullPointerException("Customer name is Empty");
			}
			List<Transcations> customerData = transcationrepository.findByCustomerName(customerName.trim());
			Map<String, List<Transcations>> transactionOfMonth = customerData.stream().collect(Collectors
					.groupingBy(x -> x.getTransactionDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH)));
			for (Map.Entry<String, List<Transcations>> monthEntry : transactionOfMonth.entrySet()) {
				String month = monthEntry.getKey();
				List<Transcations> monthTranscations = monthEntry.getValue();
				sum = monthTranscations.stream().mapToInt(this::calculatePoint).sum();
				customerMap.put(month, sum);

			}
			response.setCustomerName(customerName);
			response.setMonthlyPoints(customerMap);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return response;

	}

	/*
	 * Here we are calculating rewards points of all transactions per criteria
	 * mentioned as per business requirement
	 */
	@Override
	public List<CustomerReponse> calculateCustomerRewardPoints() {
		// TODO Auto-generated method stub
		// Fetching all Transactions from transcationrepository using Data JPA
		List<CustomerReponse> reponse = new ArrayList<>();
		try {
			List<Transcations> allCustomersTransactions = transcationrepository.findAll();
			// checking empty list
			if (!allCustomersTransactions.isEmpty()) {
				// Converting into Map all List<transactions> for sorting and grouping as
				// CustomerId which is unique
				Map<Long, List<Transcations>> transctions = allCustomersTransactions.stream()
						.collect(Collectors.groupingBy(Transcations::getId));
				// To return response creating Object CustomerResponse Object as ArrayList()

				// transaction by customer

				// Using this for Loop checking every List<transaction> of a customer and and
				// grouping as per monthly basis
				for (Map.Entry<Long, List<Transcations>> entry : transctions.entrySet()) {
					List<Transcations> custTrans = entry.getValue();
					String customerName = custTrans.get(0).getCustomerName();
					CustomerReponse custResponse = new CustomerReponse(customerName);
					// Transactions happened by month
					Map<String, List<Transcations>> transcationOfMonth = custTrans.stream()
							.collect(Collectors.groupingBy(x -> x.getTransactionDate().getMonth()
									.getDisplayName(TextStyle.FULL, Locale.ENGLISH)));
					// Using this for loop calculating every customers monthly rewards points
					for (Map.Entry<String, List<Transcations>> monthEntry : transcationOfMonth.entrySet()) {
						String month = monthEntry.getKey();
						List<Transcations> monthTranscations = monthEntry.getValue();
						// sum of transaction happened in month calling method calculatePoint
						int monthlyPoints = monthTranscations.stream().mapToInt(this::calculatePoint).sum();
						// set Month and monthly points
						custResponse.getMonthlyPoints().put(month, monthlyPoints);
						// add total points earned in every month
						custResponse.setTotal(custResponse.getTotal() + monthlyPoints);
					}

					reponse.add(custResponse);

				}

			}
		} catch (Exception e) {
			return reponse;
		}
		// returning all customer data in List<Transactions>
		return reponse;

	}

	/*
	 * Here we are calculating rewards points as per criteria mentioned as per
	 * business requirement
	 */
	public int calculatePoint(Transcations transaction) {
		double amount = transaction.getAmount();
		int point = 0;
		if (amount > 100) {
			point += (int) ((amount - 100) * 2);
			point += 50;
		} else if (amount > 50) {
			point += (int) (amount - 50);
		}
		return point;
	}

}
