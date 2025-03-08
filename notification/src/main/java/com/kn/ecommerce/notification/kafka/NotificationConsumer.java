package com.kn.ecommerce.notification.kafka;

import com.kn.ecommerce.kafka.order.OrderConfirmation;
import com.kn.ecommerce.notification.email.EmailService;
import com.kn.ecommerce.notification.kafka.common.Customer;
import com.kn.ecommerce.kafka.payment.PaymentConfirmation;
import com.kn.ecommerce.notification.notification.Notification;
import com.kn.ecommerce.notification.notification.NotificationRepository;
import com.kn.ecommerce.notification.notification.NotificationType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccess(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Consuming the message from the payment topic:: {}", paymentConfirmation);
        Notification notification = Notification
                .builder()
                .paymentConfirmation(paymentConfirmation)
                .notificationType(NotificationType.PAYMENT_CONFIRMATION)
                .date(LocalDateTime.now())
                .build();

        Customer customer = paymentConfirmation.customer();
        emailService.sendPaymentConfirmationEmail(customer.email(),customer.firstName()+" "+customer.lastName(),paymentConfirmation.amount(),paymentConfirmation.orderId().toString());
        notificationRepository.save(notification);
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderSuccess(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Consuming the message from the order topic:: {}", orderConfirmation);
        Notification notification = Notification
                .builder()
                .orderConfirmation(orderConfirmation)
                .notificationType(NotificationType.ORDER_CONFIRMATION)
                .date(LocalDateTime.now())
                .build();
        notificationRepository.save(notification);
        Customer customer = orderConfirmation.customer();
        emailService
                .sendOrderConfirmationEmail(
                        customer.email(),
                        customer.firstName()+" "+customer.lastName(),
                        orderConfirmation.totalAmount(),
                        orderConfirmation.orderRef().toString(),
                        orderConfirmation.orderItems());

    }

}
