package com.example.assignment.retailer.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.assignment.retailer.entity.Transcations;
import com.fasterxml.jackson.databind.ObjectMapper;
@SpringBootTest
@AutoConfigureMockMvc
class RewardPointsControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private List<Transcations> transactions;
	
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
