package com.cegal.controller;

import com.cegal.avro.model.CustomerAddress;
import com.cegal.component.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RESTController {

    private final KafkaProducer producer;

    @Autowired
    public RESTController(KafkaProducer producer) {
        this.producer = producer;
    }


    @GetMapping(value = "/heartbeat")
    @Description("Heartbeat endpoint, for use in life sign checks.")
    public String heartbeat() {
        return "Assignment-REST - heartbeat page";
    }

    @PostMapping(
            value = "/customerAddress",
            consumes = "application/json")
    @Description("Submit customer info.")
    public ResponseEntity<com.cegal.model.CustomerAddress> postCustomerAddress(@RequestBody com.cegal.model.CustomerAddress customerAddress) {

        CustomerAddress convertedAddress = CustomerAddress.newBuilder()
                .setName(customerAddress.getName())
                .setCity(customerAddress.getCity())
                .setAreaCode(customerAddress.getAreaCode())
                .setStreet(customerAddress.getStreet())
                .setPoBox(customerAddress.getPoBox())
                .setPhoneNumber(customerAddress.getPhoneNumber())
                .build();

        producer.submitToTopic(convertedAddress);

        return ResponseEntity
                .status(201)
                .body(customerAddress);
    }


}
