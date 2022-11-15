package com.cegal.controller;

import com.cegal.component.KafkaProducer;
import com.cegal.model.CustomerAddress;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
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
                .setCity("City")
                .setAreaCode("AreaCode")
                .setStreet("Street")
                .setPoBox(5555555L)
                .setPhoneNumber(98765432L)
                .build();

        ResponseEntity<CustomerAddress> response = controller.postCustomerAddress(expected);
        assertEquals(201, response.getStatusCode().value());

        verify(producer).submitToTopic(argThat(arg ->
                expected.getName().equals(arg.getName().toString()) &&
                        expected.getCity().equals(arg.getCity().toString()) &&
                        expected.getAreaCode().equals(arg.getAreaCode().toString()) &&
                        expected.getStreet().equals(arg.getStreet().toString()) &&
                        expected.getPoBox().equals(arg.getPoBox()) &&
                        expected.getPhoneNumber().equals(arg.getPhoneNumber())));

        CustomerAddress actual = response.getBody();
        assertNotNull(actual);
        assertAll(
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getCity(), actual.getCity()),
                () -> assertEquals(expected.getAreaCode(), actual.getAreaCode()),
                () -> assertEquals(expected.getStreet(), actual.getStreet()),
                () -> assertEquals(expected.getPoBox(), actual.getPoBox()),
                () -> assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber())
        );
    }

}