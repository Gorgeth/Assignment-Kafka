package com.cegal.avro;

import com.cegal.avro.model.CustomerAddress;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class CustomerAddressDeSerializer implements Serializer<CustomerAddress>, Deserializer<CustomerAddress> {

    @Override
    public void close() {
    }

    @Override
    public void configure(Map<String, ?> arg0, boolean arg1) {
    }

    @Override
    public byte[] serialize(String topic, CustomerAddress data) {
        try {
            byte[] result = null;

            if (data != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                BinaryEncoder binaryEncoder =
                        EncoderFactory.get().binaryEncoder(byteArrayOutputStream, null);

                DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(data.getSchema());
                datumWriter.write(data, binaryEncoder);

                binaryEncoder.flush();
                byteArrayOutputStream.close();

                result = byteArrayOutputStream.toByteArray();
            }
            return result;
        } catch (IOException exception) {
            throw new SerializationException("Unable to serialize object: " + data, exception);
        }
    }

    @Override
    public CustomerAddress deserialize(String topic, byte[] data) {
        try {
            CustomerAddress result = null;

            if (data != null) {
                DatumReader<GenericRecord> datumReader = new SpecificDatumReader<>(CustomerAddress.getClassSchema());
                Decoder decoder = DecoderFactory.get().binaryDecoder(data, null);

                result = (CustomerAddress) datumReader.read(null, decoder);
            }
            return result;
        } catch (IOException exception) {
            throw new SerializationException("Unable to deserialize object: " + Arrays.toString(data), exception);
        }
    }
}
