/* 
 * In this class we are writing Junit test cases API calls for RewardPointsController class
 *  */
package com.example.assignment.retailer.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.assignment.retailer.entity.Transcations;
import com.example.assignment.retailer.model.CustomerReponse;
import com.example.assignment.retailer.service.CustomerRewardServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
@SpringBootTest
@AutoConfigureMockMvc
class RewardPointsControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Mock
	public CustomerRewardServiceImpl customerRewardServiceImpl;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private List<Transcations> transactions;
	
	// In this method we are mocking List of transactions 
	@BeforeEach
	void setup()
	{
		transactions = Arrays.asList(new Transcations(14l,"PraveenK",  LocalDate.of(2024, 7, 5),70),
		new Transcations(14l,"PraveenK",  LocalDate.of(2024, 7, 25),70),
		new Transcations(13l,"AvinashM",  LocalDate.of(2024, 5, 20),100),
		new Transcations(12l,"PrashantJ",  LocalDate.of(2024, 6, 10),120),
		new Transcations(13l,"AvinashM",  LocalDate.of(2024, 5, 25),130),
		new Transcations(14l,"PraveenK",  LocalDate.of(2024, 7,15),120));
	}
	
	// In this test case we are Mocking GET Api for single specific customer PrashantJ
	@Test
	public void getCustomerTranscationsTest()throws Exception
	{	
		String transcationJson = objectMapper.writeValueAsString(transactions);
		String customerName="PrashantJ";
		mockMvc.perform(get("/getCustomer/"+customerName)
		.contentType(MediaType.APPLICATION_JSON)
		.content(transcationJson))
		.andExpect(status().isOk());
	}
	
	// In this test case we are Mocking GET Api for All customer
	@Test
	public void getAllCustomerTest() throws Exception
	{
		String transcationJson = objectMapper.writeValueAsString(transactions);
		mockMvc.perform(get("/getAllCustomer")
		.contentType(MediaType.APPLICATION_JSON)
		.content(transcationJson))
		.andExpect(status().isOk());	
	}
	

}
