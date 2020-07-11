package com.example.WeatherInfoApp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.WeatherInfoApp.model.Zipcode;
import com.example.WeatherInfoApp.repository.ZipcodeRepository;
import com.example.WeatherInfoApp.response.Response;

@Service
public class WeatherService {

  @Autowired
  private ZipcodeRepository zipcodeRepository;
  
  @Value("${api_key}")
  private String apiKey;
  
  public List<Zipcode> findAllByDate(){
    return zipcodeRepository.findAllByOrderByCreatedAtDesc();
  }

  public Response getWeather(String zipcode) {
    String url = "http://api.openweathermap.org/data/2.5/weather?zip="
        + zipcode + "&units=imperial&appid=" + apiKey;
    RestTemplate restTemplate = new RestTemplate();
    Response response = new Response();
    List<Zipcode> zipCodeList = zipcodeRepository.findAll();
    try {
      response = restTemplate.getForObject(url, Response.class);
      Zipcode zip = new Zipcode();
      zip.setZipcode(zipcode);
      zipCodeList.add(zip);
      saveZipcode(zip);
    } 
    catch(HttpClientErrorException ex) {
      response.setName("error");
    }
    return response;
  }

  public void saveZipcode(Zipcode zip) {
    zipcodeRepository.save(zip);
  }
  
  public List<Zipcode> getLastTenZips() {
    List<Zipcode> zipCodeList = findAllByDate();
    List<Zipcode> zipLastTen = new ArrayList<>();
    zipLoop:
      for(Zipcode zip : zipCodeList) {
        if (zipLastTen.size() <= 10) {
          zipLastTen.add(zip);
        } else {
          break zipLoop;
        }
      }
    return zipLastTen;
  }
   
}