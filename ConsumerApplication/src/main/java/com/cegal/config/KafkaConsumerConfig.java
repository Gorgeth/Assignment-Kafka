package com.cegal.config;

import com.cegal.avro.CustomerAddressDeSerializer;
import com.cegal.avro.model.CustomerAddress;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${kafka.host}")
    private String kafkaAddress;
    @Value(value = "${kafka.group.id}")
    private String groupID;


    /**
     * Configure the kafka properties required for consumption
     */
    @Bean
    public ConsumerFactory<String, CustomerAddress> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomerAddressDeSerializer.class);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupID);

        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), new CustomerAddressDeSerializer());
    }

    /**
     * Creates the kafka listener bean used by KafkaConsumer
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CustomerAddress> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CustomerAddress> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }
}
