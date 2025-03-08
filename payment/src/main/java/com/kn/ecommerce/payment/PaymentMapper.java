package com.kn.ecommerce.payment;

import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment toPayment(PaymentRequest paymentRequest) {
        return Payment.builder()
                .paymentMethod(PaymentMethod.valueOf(paymentRequest.paymentMethod()))
                .amount(paymentRequest.amount())
                .orderId(paymentRequest.orderId())
                .build();
    }

    public PaymentResponse toPaymentResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getOrderId(),
                payment.getPaymentMethod().name(),
                payment.getAmount()
        );
    }
}
