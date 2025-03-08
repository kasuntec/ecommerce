package com.kn.ecommerce.payment;


import com.kn.ecommerce.kafka.payment.PaymentConfirmation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentMessageProducer {

    @Value("${spring.application.config.kafka.payment-topic}")
    private String orderTopic;
    private final KafkaTemplate<String, PaymentConfirmation> kafkaTemplate;

    public void sendOrderConfirmation(PaymentConfirmation orderConfirmation) {
        Message<PaymentConfirmation> message = MessageBuilder
                .withPayload(orderConfirmation)
                .setHeader(KafkaHeaders.TOPIC,orderTopic)
                .build();
        kafkaTemplate.send(message);
    }
}
