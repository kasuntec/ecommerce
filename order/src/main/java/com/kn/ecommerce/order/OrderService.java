package com.kn.ecommerce.order;

import com.kn.ecommerce.client.customer.CustomerClient;
import com.kn.ecommerce.client.payment.Customer;
import com.kn.ecommerce.client.payment.PaymentClient;
import com.kn.ecommerce.client.payment.PaymentRequest;
import com.kn.ecommerce.client.product.ProductClient;
import com.kn.ecommerce.kafka.order.OrderConfirmation;
import com.kn.ecommerce.order.kafka.OrderMessageProducer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMessageProducer orderMessageProducer;
    private final PaymentClient paymentClient;

    @Transactional
    public OrderResponse save(@Valid OrderRequest orderRequest) {
        Order order = orderMapper.toOrder(orderRequest);
        var customerById = customerClient
                .getCustomerById(orderRequest.customerId())
                .orElseThrow(()-> new OrderNotFoundException("Can't place order since customer not exits, customer id: "+orderRequest.customerId()));
        order.setCustomerId(customerById.id());

        orderRepository.save(order);
        List<OrderLineResponse> orderLineResponse =new ArrayList<>();
        order.getOrderLines().forEach(orderLine -> {

            var productById = productClient
                    .getProductById(orderLine.getProductId())
                    .orElseThrow(()-> new OrderNotFoundException("Can't place order since product not exits, product id: "+orderLine.getProductId()));

            orderLine.setOrder(order);
            orderLine.setProductId(productById.id());
            orderLineRepository.save(orderLine);

            orderLineResponse.add(orderMapper.buildOrderItem(orderLine, productById));
        });

        OrderResponse orderResponse = orderMapper.toOrderResponse(order, customerById, orderLineResponse);

        Customer customer = new Customer(
                 customerById.id(),
                customerById.firstName(),
                customerById.lastName(),
                customerById.email()
        );
        paymentClient.makePayment(
                new PaymentRequest(
                        order.getId(),
                        customer.id(),
                        order.getPaymentMethod().name(),
                        order.getTotalAmount()
                )
        );

        //send kafka message to order confirmation
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        orderMessageProducer
                .sendOrderConfirmation(
                        new OrderConfirmation(
                                order.getId(),
                                order.getTotalAmount(),
                                orderResponse.customer(),
                                order.getCreatedAt(),
                                orderResponse.orderLines())
                );

        return orderResponse;
    }


    public OrderResponse findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found. id: "+id));
        List<OrderLineResponse> orderLineResponse = order.getOrderLines().stream().map(orderLine -> {
            var productById = productClient
                    .getProductById(orderLine.getProductId())
                    .orElseThrow(()-> new OrderNotFoundException("product not exits, customer id: "+orderLine.getProductId()));
            return orderMapper.buildOrderItem(orderLine, productById);
        }).toList();

        var customerById = customerClient
                .getCustomerById(order.getCustomerId())
                .orElseThrow(()-> new OrderNotFoundException("Can't place order since customer not exits, customer id: "+order.getCustomerId()));
        return  orderMapper.toOrderResponse(order,customerById, orderLineResponse);
    }

    public Collection<OrderResponse> findAll() {
        return orderRepository
                .findAll()
                .stream()
                .map(order ->  {
                    var customerById = customerClient
                            .getCustomerById(order.getCustomerId())
                            .orElseThrow(()-> new OrderNotFoundException("Can't place order since customer not exits, customer id: "+order.getCustomerId()));
                    return orderMapper.toOrderResponse(order,customerById, Collections.EMPTY_LIST);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long id) {
        orderLineRepository.deleteAllByOrderId(id);
        orderRepository.deleteById(id);
    }
}
