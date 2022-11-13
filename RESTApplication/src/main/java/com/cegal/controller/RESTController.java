package com.cegal.controller;

import com.cegal.avro.model.CustomerAddress;
import com.cegal.component.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RESTController {



  @GetMapping(value = "/heartbeat")
  @Description("Heartbeat endpoint, for use in life sign checks.")
  public String form() {
    return "Assignment-REST - heartbeat page";
  }

  @PostMapping(
          value = "/api/customerInfo",
          consumes = "application/json")
  @Description("Submit customer info.")
  public ResponseEntity<com.cegal.model.CustomerAddress> postCustomerAddress(@RequestBody com.cegal.model.CustomerAddress customerAddress) {

    CustomerAddress convertedAddress = CustomerAddress.newBuilder()
            .setName(customerAddress.getName())
            .setStreet(customerAddress.getStreet())
            .setPostcode(customerAddress.getPostcode())
            .build();

//    producer.submitToTopic(convertedAddress);
    System.out.println("Customer info has been submitted: " + convertedAddress.toString());

    return ResponseEntity
            .status(201)
            .body(customerAddress);
  }



}
