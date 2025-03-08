package com.kn.ecommerce.order;

import com.kn.ecommerce.client.customer.CustomerResponse;
import com.kn.ecommerce.client.product.ProductResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public Order toOrder(OrderRequest orderRequest) {
        List<OrderLine> orderLines = orderRequest
                .orderLines()
                .stream().map(orderLineRequest -> {
                    return OrderLine.builder().productId(orderLineRequest.productId())
                            .qty(orderLineRequest.qty())
                            .price(orderLineRequest.price())
                            .build();
                })
                .collect(Collectors.toList());

        return Order
                .builder()
                .customerId(orderRequest.customerId())
                .paymentMethod(PaymentMethod.valueOf(orderRequest.paymentMethod()))
                .totalAmount(orderRequest.totalAmount())
                .orderLines(orderLines)
                .build();
    }

    public OrderResponse toOrderResponse(Order order, CustomerResponse customer,List<OrderLineResponse> orderLineResponses ) {
        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getPaymentMethod().name(),
                customer,
                order.getCreatedAt(),
                order.getLastUpdated(),
                orderLineResponses
        );
    }

    public OrderLineResponse buildOrderItem(OrderLine orderLine, ProductResponse productResponse) {
        return new OrderLineResponse(orderLine.getId(),
                orderLine.getOrder().getId(),
                orderLine.getProductId(),
                productResponse.name(),
                orderLine.getPrice(),
                orderLine.getQty());
    }
}
