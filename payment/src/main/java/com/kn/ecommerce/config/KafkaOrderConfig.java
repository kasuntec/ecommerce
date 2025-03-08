package com.kn.ecommerce.config;

import com.kn.ecommerce.kafka.payment.PaymentConfirmation;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaOrderConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String boostrapServers;

    public NewTopic orderTopic(){
       return TopicBuilder
               .name("${application.config.kafka.payment-topic}")
               .build();
    }

    @Bean
    public Map<String,Object> producerConfig(){
        Map<String, Object> props =new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String,PaymentConfirmation> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, PaymentConfirmation> kafkaTemplate(
            ProducerFactory<String,PaymentConfirmation> producerFactory
    ){
        return new KafkaTemplate<>(producerFactory);
    }
}
