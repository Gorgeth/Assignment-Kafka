package com.cegal.component;

import com.cegal.avro.model.CustomerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class KafkaProducer {

  private final KafkaTemplate<String, String> kafkaTemplate;

  @Value(value = "${kafka.topic}")
  private String topic;


  @Autowired
  public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }


  public void submitToTopic(CustomerAddress customerAddress) {
    ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, customerAddress.toString());

    future.addCallback(new ListenableFutureCallback<>() {

      @Override
      public void onSuccess(SendResult<String, String> result) {
        System.out.println("Sent message=[" + customerAddress +
                "] with offset=[" + result.getRecordMetadata().offset() + "]");
      }
      @Override
      public void onFailure(Throwable ex) {
        System.out.println("Unable to send message=[" + customerAddress + "] due to : " + ex.getMessage());
      }
    });
  }
}
