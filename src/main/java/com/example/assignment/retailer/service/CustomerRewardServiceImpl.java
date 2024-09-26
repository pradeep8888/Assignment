package com.example.assignment.retailer.service;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.retailer.entity.Transcations;
import com.example.assignment.retailer.model.Customer;
import com.example.assignment.retailer.model.CustomerReponse;
import com.example.assignment.retailer.model.Points;
import com.example.assignment.retailer.repository.TranscationRepository;

@Service
public class CustomerRewardServiceImpl implements CustomerRewardService {

	
	@Autowired
	private TranscationRepository transcationrepository;
	
	@Override
	public List<Transcations> getAllCustomer() {
		
		List<Transcations> allCustomers = transcationrepository.findAll();
		return allCustomers;
	}
	
	@Override
	public Customer getCustomerTranscations(String customerName) {
		int sum =0;
		Customer response= new Customer();
		Map<String,Integer> customerMap = new HashMap<>();
		try {
			if(customerName.isBlank() || customerName==null)
			{
				throw new NullPointerException("Customer name is Empty");
			}
			List<Transcations> customerData = transcationrepository.findByCustomerName(customerName);
			Map<String, List<Transcations>> transactionOfMonth = customerData.stream().collect(Collectors.groupingBy(x->x.getTransactionDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH)));		
			for(Map.Entry<String, List<Transcations>> monthEntry:transactionOfMonth.entrySet())
			{
				String month = monthEntry.getKey();
				List<Transcations> monthTranscations = monthEntry.getValue();			
				sum= monthTranscations.stream().mapToInt(this::calculatePoint).sum();
				customerMap.put(month,sum);
	
			}		
		response.setCustomerName(customerName);
		response.setMonthlyPoints(customerMap);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return response;
		
	}

	

	@Override
	public List<CustomerReponse> calculateCustomerRewardPoints() {
		// TODO Auto-generated method stub
		
		List<Transcations> allCustomers = transcationrepository.findAll();
		Map<Long,List<Transcations>> transctions = allCustomers.stream().collect(Collectors.groupingBy(Transcations::getId));
		
		List<CustomerReponse> reponse = new ArrayList<>();
		//transcation by customer
		for(Map.Entry<Long,List<Transcations>> entry:transctions.entrySet())
		{
			Long id = entry.getKey();
			List<Transcations> custTrans=entry.getValue();
			String customerName = custTrans.get(0).getCustomerName();
			
			CustomerReponse custResponse = new CustomerReponse(customerName);
			// transcations happened by month
			Map<String,List<Transcations>> transcationOfMonth = custTrans.stream().collect(Collectors.groupingBy(x->x.getTransactionDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH)));
			
			for(Map.Entry<String,List<Transcations>> monthEntry:transcationOfMonth.entrySet())
			{
				String month = monthEntry.getKey();
				List<Transcations> monthTranscations = monthEntry.getValue();
				int monthlyPoints = monthTranscations.stream().mapToInt(this::calculatePoint).sum();
			
				custResponse.getMonthlyPoints().put(month, monthlyPoints);
				custResponse.setTotal(custResponse.getTotal()+monthlyPoints);
				
			}
			reponse.add(custResponse);
			
		}
		return reponse;
	}
	
	
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
