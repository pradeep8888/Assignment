package com.example.assignment.retailer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.assignment.retailer.entity.Transcations;

public interface TranscationRepository extends JpaRepository<Transcations, Long> {

	public List<Transcations> findByCustomerName(String name);
}
