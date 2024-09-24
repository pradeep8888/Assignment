package com.example.assignment.retailer.service;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.assignment.retailer.entity.Transcations;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerRewardServiceImplTest {

	@InjectMocks
	public CustomerRewardServiceImpl customerRewardServiceImpl;
	
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
