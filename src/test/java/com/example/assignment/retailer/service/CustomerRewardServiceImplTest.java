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
		List<Transcations> mockTranscationslist = Arrays.asList(
				new Transcations(14l,"AvinashM",  LocalDate.of(2024, 8, 5),190),
				new Transcations(11l,"AvinashM",  LocalDate.of(2024, 7, 15),120)
				);
		when(transcationrepository.findByCustomerName("AvinashM")).thenReturn(mockTranscationslist);		
		Customer customerTranscations = customerRewardServiceImpl.getCustomerTranscations("AvinashM");
		assertEquals(90,customerTranscations.getMonthlyPoints().get("July"));
		
	}
	
	@Test
	public void calculateCustomerRewardPointsTest()
	{
		List<Transcations> mockTranscationslist = Arrays.asList(
				new Transcations(14l,"AvinashM",  LocalDate.of(2024, 7, 5),120),
				new Transcations(13l,"PraveenK",  LocalDate.of(2024, 6, 5),120),
				new Transcations(11l,"AvinashM",  LocalDate.of(2024, 7, 15),130)
				);
		when(transcationrepository.findAll()).thenReturn(mockTranscationslist);		
		List<CustomerReponse> calculateCustomerRewardPointsList = customerRewardServiceImpl.calculateCustomerRewardPoints();
		CustomerReponse customerReponse = calculateCustomerRewardPointsList.get(1);
		assertEquals(2, calculateCustomerRewardPointsList.size(),"Customer should have 3 months Transactions");
		assertEquals(90,customerReponse.getMonthlyPoints().get("June"),"Jully rewards points should be 90");
	}
	@Test
	public void calculateCustomerRewardPointsTest_NoTranscations()
	{
		when(transcationrepository.findAll()).thenReturn(Collections.EMPTY_LIST);		
		List<CustomerReponse> calculateCustomerRewardPointsList = customerRewardServiceImpl.calculateCustomerRewardPoints();
		assertEquals(0, calculateCustomerRewardPointsList.size(),"There should be no customer then there are no transactions.");
		
	}
	
	@Test
	public void calculateCustomerRewardPointsTest_Empty_List()
	{
		when(transcationrepository.findAll()).thenReturn(null);		
		// Act & assert an exception 
		assertThrows(NullPointerException.class, ()->{
			 customerRewardServiceImpl.calculateCustomerRewardPoints();	
		});
	}
	
	@Test
	public void calculatePointTest_NoPoints()
	{
		Transcations transcations=new Transcations();
		transcations.setAmount(50);
		int points = customerRewardServiceImpl.calculatePoint(transcations);
		assertEquals(0, points);
	}
	
	@Test
	public void calculatePointTest_LessAmount()
	{
		Transcations transcations=new Transcations();
		transcations.setAmount(30);
		int points = customerRewardServiceImpl.calculatePoint(transcations);
		assertEquals(0, points);
	}
	
	@Test
	public void calculatePointTest_Bet50To100()
	{
		Transcations transcations=new Transcations();
		transcations.setAmount(75);
		int points = customerRewardServiceImpl.calculatePoint(transcations);
		assertEquals(25, points,"Points should be 25 purchase points Of $75");
	}
	
	@Test
	public void calculatePointTest_Over100()
	{
		Transcations transcations=new Transcations();
		transcations.setAmount(120);
		int points = customerRewardServiceImpl.calculatePoint(transcations);
		assertEquals(90, points,"Points should be 90(2x$20 + 1x$50) for points Of $120");
	}
	
	@Test
	public void calculatePointTest_Over200()
	{
		Transcations transcations=new Transcations();
		transcations.setAmount(250);
		int points = customerRewardServiceImpl.calculatePoint(transcations);
		assertEquals(350, points,"Points should be 350 for purchase points Of $250(2*150+1*50)");
	}
	
}
