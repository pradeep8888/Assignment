/*
 * 
 * In this class we are writing Junit test cases for CustomerRewardServiceImpl class
 *  */
package com.example.assignment.retailer.service;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.assignment.retailer.entity.Transcations;
import com.example.assignment.retailer.model.Customer;
import com.example.assignment.retailer.model.CustomerReponse;
import com.example.assignment.retailer.repository.TranscationRepository;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerRewardServiceImplTest {

	@InjectMocks
	public CustomerRewardServiceImpl customerRewardServiceImpl;
	
	@Mock
	private TranscationRepository transcationrepository;
	
	@BeforeEach
	public void setup()
	{
		MockitoAnnotations.openMocks(this);  
	}
	
	@Test
	public void getCustomerTranscationsTest()
	{
		// Prepare mock transactions
		List<Transcations> mockTranscationslist = Arrays.asList(
				new Transcations(14l,"AvinashM",  LocalDate.of(2024, 8, 5),190),
				new Transcations(11l,"AvinashM",  LocalDate.of(2024, 7, 15),120)
				);
		
		// Mock the repository call
		
		when(transcationrepository.findByCustomerName("AvinashM")).thenReturn(mockTranscationslist);
		// Call the service method
		Customer customerTranscations = customerRewardServiceImpl.getCustomerTranscations("AvinashM");
		
		// Verify or Assert the result
		assertEquals(90,customerTranscations.getMonthlyPoints().get("July"));
		
	}
	
	@Test
	public void calculateCustomerRewardPointsTest()
	{ 
		// Prepare mock transactions
		List<Transcations> mockTranscationslist = Arrays.asList(
				new Transcations(14l,"AvinashM",  LocalDate.of(2024, 7, 5),120),
				new Transcations(13l,"PraveenK",  LocalDate.of(2024, 6, 5),120),
				new Transcations(11l,"AvinashM",  LocalDate.of(2024, 7, 15),130)
				);
		
		// Mock the repository call
		when(transcationrepository.findAll()).thenReturn(mockTranscationslist);	
		
		// Call the service method
		List<CustomerReponse> calculateCustomerRewardPointsList = customerRewardServiceImpl.calculateCustomerRewardPoints();
		
		// Verify the result
		assertEquals(2, calculateCustomerRewardPointsList.size(),"Customer should have 3 months Transactions");
		
		CustomerReponse customerReponse = calculateCustomerRewardPointsList.get(1);
		
		//PraveenK should have 1 months of transactions
		assertEquals(90,customerReponse.getMonthlyPoints().get("June"),"June rewards points should be 90");
	}

	@Test
    void testCalculateCustomerRewardPoints_EmptyTransactions() {
        // Mock an empty list of transactions
        when(transcationrepository.findAll()).thenReturn(Collections.emptyList());

        // Call the service method
        List<CustomerReponse> result = customerRewardServiceImpl.calculateCustomerRewardPoints();

        // Verify that the result is empty
        assertTrue(result.isEmpty(), "The result should be an empty list when there are no transactions.");
    }
	
	@Test
	public void calculateCustomerRewardPointsTest_NoTranscations()
	{
		// Mock an empty list of transactions
		when(transcationrepository.findAll()).thenReturn(Collections.EMPTY_LIST);		
		
		// Call the service method
		List<CustomerReponse> calculateCustomerRewardPointsList = customerRewardServiceImpl.calculateCustomerRewardPoints();
		
		// Verify that the result is empty
		assertEquals(0, calculateCustomerRewardPointsList.size(),"There should be no customer then there are no transactions.");
		
	}
	
	@Test
	public void calculateCustomerRewardPointsTest_Empty_List()
	{
		// Mock an null value
		when(transcationrepository.findAll()).thenReturn(null);		
		// Act & assert an exception 
		assertThrows(NullPointerException.class, ()->{
			 customerRewardServiceImpl.calculateCustomerRewardPoints();	
		});
	}
	
	@Test
	public void calculatePointTest_50Points()
	{
		// Prepare mock transaction
		Transcations transcations=new Transcations();
		transcations.setAmount(50);
		// Call the service method
		int points = customerRewardServiceImpl.calculatePoint(transcations);
		
		// Verify that the result is empty
		assertEquals(0, points);
	}
	
	@Test
	public void calculatePointTest_LessAmount()
	{
		// Prepare mock transaction and set value less than 50
		Transcations transcations=new Transcations();
		transcations.setAmount(30);
		// Call the service method
		int points = customerRewardServiceImpl.calculatePoint(transcations);
		// Verify that the result is 0
		assertEquals(0, points);
	}
	
	@Test
	public void calculatePointTest_Bet50To100()
	{
		// Prepare mock transaction and set value between 50 to 100
		Transcations transcations=new Transcations();
		transcations.setAmount(75);
		// Call the service method
		int points = customerRewardServiceImpl.calculatePoint(transcations);
		// Verify that the result is 25
		assertEquals(25, points,"Points should be 25 purchase points Of $75");
	}
	
	@Test
	public void calculatePointTest_Over100()
	{
		// Prepare mock transaction and set value over 100
		Transcations transcations=new Transcations();
		transcations.setAmount(120);
		// Call the service method
		int points = customerRewardServiceImpl.calculatePoint(transcations);
		// Verify that the result is 90
		assertEquals(90, points,"Points should be 90(2x$20 + 1x$50) for points Of $120");
	}
	
	@Test
	public void calculatePointTest_Over200()
	{
		// Prepare mock transaction and set value over 200
		Transcations transcations=new Transcations();
		transcations.setAmount(250);
		// Call the service method
		int points = customerRewardServiceImpl.calculatePoint(transcations);
		// Verify that the result is 350
		assertEquals(350, points,"Points should be 350 for purchase points Of $250(2*150+1*50)");
	}
	
}
