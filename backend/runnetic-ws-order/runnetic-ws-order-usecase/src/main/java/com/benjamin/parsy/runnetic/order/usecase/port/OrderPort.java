package com.benjamin.parsy.runnetic.order.usecase.port;

import com.benjamin.parsy.runnetic.order.usecase.exception.OrderNotFoundException;
import com.benjamin.parsy.runnetic.order.entity.model.Order;

import java.util.UUID;

public interface OrderPort {

    /**
     * To save an order
     *
     * @param order Order to save
     * @return The order saved
     */
    Order save(Order order);

    /**
     * To update an order
     *
     * @param order Order to update
     */
    void update(Order order);

    /**
     * To search for an order by its identifier
     *
     * @param uuid Order identifier
     * @return The order optionally found
     * @throws OrderNotFoundException Throw when order is not found
     */
    Order findById(UUID uuid) throws OrderNotFoundException;

}
