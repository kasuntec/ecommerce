package com.kn.ecommerce.payment;

import com.kn.ecommerce.client.customer.CustomerClient;
import com.kn.ecommerce.client.customer.CustomerResponse;
import com.kn.ecommerce.exception.PaymentNotFoundException;
import com.kn.ecommerce.kafka.payment.PaymentConfirmation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final PaymentMessageProducer paymentMessageProducer;
    private final CustomerClient customerClient;

    public PaymentResponse findById(Long id) {
        Payment payment = paymentRepository
                .findById(id).orElseThrow(() -> new PaymentNotFoundException("Customer not found, id" + id));
        return paymentMapper.toPaymentResponse(payment);
    }

    public PaymentResponse save(@Valid PaymentRequest paymentRequest) {
        Payment payment = paymentMapper.toPayment(paymentRequest);
        paymentRepository.save(payment);

        CustomerResponse customerResponse = customerClient.getCustomerById(paymentRequest.customerId()).get();

        Customer customer = new Customer(
                customerResponse.id(),
                customerResponse.firstName(),
                customerResponse.lastName(),
                customerResponse.email()
        );
        paymentMessageProducer.sendOrderConfirmation(
                new PaymentConfirmation(
                        payment.getId(),
                        payment.getOrderId(),
                        payment.getPaymentMethod().name(),
                        customer,
                        payment.getAmount()
                )
        );

        return paymentMapper.toPaymentResponse(payment);
    }
}
