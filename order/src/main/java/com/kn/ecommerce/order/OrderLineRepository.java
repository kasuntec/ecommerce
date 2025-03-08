package com.kn.ecommerce.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine,Long> {
    void deleteAllByOrderId(Long orderId);
}
