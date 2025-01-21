package com.benjamin.parsy.ksb.order.domain.repository;

import com.benjamin.parsy.ksb.order.domain.model.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    /**
     * To save an order
     *
     * @param order Order to save
     * @return The order saved
     */
    Order save(Order order);

    /**
     * To search for an order by its identifier
     *
     * @param uuid Order identifier
     * @return The order optionally found
     */
    Optional<Order> findById(UUID uuid);

}
