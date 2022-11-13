package com.cegal.controller;

import com.cegal.component.KafkaProducer;
import com.cegal.model.CustomerAddress;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class RESTControllerTest {

    @Mock
    private KafkaProducer producer;

    @InjectMocks
    private RESTController controller;


    @Test
    void postCustomerAddress() {
        CustomerAddress expected = CustomerAddress.builder()
                .setName("Name")
                .setStreet("Street")
                .setPostcode("1010")
                .build();

        ResponseEntity<CustomerAddress> response = controller.postCustomerAddress(expected);
        assertEquals(201, response.getStatusCode().value());

        verify(producer).submitToTopic(argThat(arg ->
                expected.getName().equals(arg.getName().toString()) &&
                expected.getPostcode().equals(arg.getPostcode().toString()) &&
                expected.getStreet().equals(arg.getStreet().toString())));

        CustomerAddress actual = response.getBody();
        assertNotNull(actual);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getStreet(), actual.getStreet());
        assertEquals(expected.getPostcode(), actual.getPostcode());
    }

}