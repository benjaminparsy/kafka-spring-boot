package com.benjamin.parsy.ksb.order.order;

import com.benjamin.parsy.ksb.order.userprojection.UserProjection;
import com.benjamin.parsy.ksb.shared.service.jpa.GenericService;

import java.time.OffsetDateTime;

public interface OrderService extends GenericService<Order> {

    /**
     * @param orderDate      Order date
     * @param orderStatus    Order status
     * @param userProjection User projection to link to the Order
     * @param totalPrice     Total price of products required
     * @return Order recorded in the database
     */
    Order createOrder(OffsetDateTime orderDate, String orderStatus,
                      UserProjection userProjection, int totalPrice);

}
