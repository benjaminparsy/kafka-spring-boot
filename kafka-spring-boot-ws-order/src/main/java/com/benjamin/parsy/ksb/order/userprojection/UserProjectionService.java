package com.benjamin.parsy.ksb.order.userprojection;

import com.benjamin.parsy.ksb.order.shared.exception.UserProjectionNotFoundException;
import com.benjamin.parsy.ksb.shared.service.jpa.GenericService;

public interface UserProjectionService extends GenericService<UserProjection> {

    UserProjection updateUser(Long userId, String name, String email, String address, String phone) throws UserProjectionNotFoundException;

}
