package com.cegal.component;

import com.cegal.avro.model.CustomerAddress;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;

@Component
public class KafkaConsumer {

    @Value(value = "${kafka.topic}")
    private String topic;
    @Value(value = "${file.output}")
    private String filePath;
    private File outputFile;
    private CountDownLatch latch = new CountDownLatch(1);


    @KafkaListener(topics = "${kafka.topic}")
    public void consume(CustomerAddress customerAddress) {
        try {
            writeToDisk(customerAddress);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Pauses the consuming thread for a second between each call
        latch.countDown();
    }


    /**
     * Simple method to write the incoming kafka message to file by serializing it again.
     *
     * @param customerAddress - avro schema object
     * @throws IOException - Exceptions should be caught in the calling method
     */
    private void writeToDisk(CustomerAddress customerAddress) throws IOException {
        if (outputFile == null || !outputFile.exists()) prepOutputFile();

        Schema schema = customerAddress.getSchema();

        DatumWriter<GenericRecord> writer = new GenericDatumWriter<>(schema);
        try (DataFileWriter<GenericRecord> fileWriter = new DataFileWriter<>(writer)) {
            fileWriter.create(schema, outputFile);

            fileWriter.append(customerAddress);
        }
    }

    /**
     * Create directories on the path given and the file itself
     */
    private void prepOutputFile() throws IOException {
        outputFile = new File(filePath);
        File directory = new File(outputFile.getParent());

        if (!directory.exists()) {
            directory.mkdirs();
        }

        outputFile.createNewFile();
    }
}
