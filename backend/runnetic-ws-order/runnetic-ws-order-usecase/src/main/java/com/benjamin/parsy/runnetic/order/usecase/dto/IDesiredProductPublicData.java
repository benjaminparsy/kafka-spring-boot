package com.benjamin.parsy.runnetic.order.usecase.dto;

import java.util.UUID;

public interface IDesiredProductPublicData {

    UUID productUuid();
    int quantity();
    double price();

}
