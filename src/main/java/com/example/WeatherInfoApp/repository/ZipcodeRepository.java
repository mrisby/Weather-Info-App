package com.example.WeatherInfoApp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.WeatherInfoApp.model.Zipcode;

@Repository
public interface ZipcodeRepository extends CrudRepository<Zipcode, Long>{

	public List<Zipcode> findAllByOrderByCreatedAtDesc();
	
	public List<Zipcode> findAll();
}
