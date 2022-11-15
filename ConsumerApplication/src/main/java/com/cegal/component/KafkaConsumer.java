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
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;

@Component
public class KafkaConsumer {

    @Value(value = "${kafka.topic}")
    private String topic;
    @Value(value = "${storage.directory}")
    private String storageDirectory;
    @Value(value = "${storage.file}")
    private String fileName;
    private File storageFile;
    private final CountDownLatch latch = new CountDownLatch(1);


    @KafkaListener(topics = "${kafka.topic}")
    public void consume(CustomerAddress customerAddress) {
        try {
            writeToDisk(customerAddress);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to write customer address to disk.", e);
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
        // Verify storage file
        Path filePath = Paths.get(storageDirectory, fileName);
        if (storageFile == null && Files.notExists(filePath)) {
            // There exists no file on the path given
            storageFile = prepareOutputFile(filePath);
        } else if (storageFile == null) {
            // There is a file, but it is not prepped.
            storageFile = filePath.toFile();
        }

        Schema schema = customerAddress.getSchema();

        DatumWriter<GenericRecord> writer = new GenericDatumWriter<>(schema);
        try (DataFileWriter<GenericRecord> fileWriter = new DataFileWriter<>(writer)) {
            fileWriter.create(schema, storageFile);

            fileWriter.append(customerAddress);
        }
    }

    /**
     * Create directories on the path given and the file itself
     */
    private File prepareOutputFile(Path filePath) throws IOException {
        // First create the parent directories
        Files.createDirectories(Paths.get(storageDirectory));

        // Then create and return the new storage file
        return Files.createFile(filePath).toFile();
    }
}
