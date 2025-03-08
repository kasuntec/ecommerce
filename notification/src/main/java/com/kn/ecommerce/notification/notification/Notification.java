package com.kn.ecommerce.notification.notification;

import com.kn.ecommerce.kafka.order.OrderConfirmation;
import com.kn.ecommerce.kafka.payment.PaymentConfirmation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Notification {
    @Id
    private String id;
    private NotificationType notificationType;
    private LocalDateTime date;
    private OrderConfirmation orderConfirmation;
    private PaymentConfirmation paymentConfirmation;

}
