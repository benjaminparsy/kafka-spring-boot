package com.benjamin.parsy.ksb.order.userprojection;

import com.benjamin.parsy.ksb.order.shared.OrderErrorCode;
import com.benjamin.parsy.ksb.order.shared.exception.UserProjectionNotFoundException;
import com.benjamin.parsy.ksb.shared.service.jpa.GenericServiceImpl;
import com.benjamin.parsy.ksb.shared.service.message.ErrorMessage;
import com.benjamin.parsy.ksb.shared.service.message.MessageService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProjectionServiceImpl extends GenericServiceImpl<UserProjection> implements UserProjectionService {

    private final UserProjectionRepository repository;
    private final MessageService messageService;

    public UserProjectionServiceImpl(UserProjectionRepository repository, MessageService messageService) {
        super(repository);
        this.repository = repository;
        this.messageService = messageService;
    }

    @Override
    public UserProjection updateUser(Long userId, String name, String email, String address, String phone) throws UserProjectionNotFoundException {

        Optional<UserProjection> userOptional = repository.findById(userId);

        if (userOptional.isEmpty()) {
            ErrorMessage errorMessage = messageService.getErrorMessage(OrderErrorCode.USERPROJECTION_NOT_FOUND.getCode(), userId);
            throw new UserProjectionNotFoundException(errorMessage);
        }

        UserProjection userProjection = userOptional.get();
        userProjection.setName(name);
        userProjection.setEmail(email);
        userProjection.setAddress(address);
        userProjection.setPhone(phone);

        return repository.save(userProjection);
    }

}
