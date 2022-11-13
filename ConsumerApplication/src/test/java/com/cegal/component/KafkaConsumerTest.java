package com.cegal.component;

import com.cegal.avro.model.CustomerAddress;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest
public class KafkaConsumerTest {

    private KafkaConsumer consumer = new KafkaConsumer();


    @Test
    void consume() {
        CustomerAddress customerAddress = CustomerAddress.newBuilder()
                .setName("Name")
                .setPostcode("Postcode")
                .setStreet("Street")
                .build();

        consumer.consume(customerAddress);

        fail();
    }
}
