package com.cegal.component;

import com.cegal.avro.model.CustomerAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class KafkaConsumerTest {

    @TempDir
    File tempDir;

    private final KafkaConsumer consumer = new KafkaConsumer();


    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(consumer, "storageDirectory", tempDir.toString());
        ReflectionTestUtils.setField(consumer, "fileName", "CustomerAddress.avro");
    }


    @Test
    void consumeExistingFile() throws IOException {
        Files.createFile(Paths.get(tempDir.toString(), "CustomerAddress.avro")).toFile().createNewFile();

        assertEquals(1, tempDir.list().length);
        assertEquals("CustomerAddress.avro", tempDir.list()[0]);
        assertEquals(0, Files.size(tempDir.listFiles()[0].toPath()));

        CustomerAddress customerAddress = createCustomerAddress();

        consumer.consume(customerAddress);

        assertEquals(1, tempDir.list().length);
        assertEquals("CustomerAddress.avro", tempDir.list()[0]);
        assertTrue(Files.size(tempDir.listFiles()[0].toPath()) > 0);
    }

    @Test
    void consumeNewFile() throws IOException {
        assertEquals(0, tempDir.list().length);

        CustomerAddress customerAddress = createCustomerAddress();

        consumer.consume(customerAddress);

        assertEquals(1, tempDir.list().length);
        assertEquals("CustomerAddress.avro", tempDir.list()[0]);
        assertTrue(Files.size(tempDir.listFiles()[0].toPath()) > 0);
    }


    private CustomerAddress createCustomerAddress() {
        return CustomerAddress.newBuilder()
                .setName("Name")
                .setCity("City")
                .setAreaCode("AreaCode")
                .setStreet("Street")
                .setPoBox(5555555L)
                .setPhoneNumber(98765432L)
                .build();
    }
}
