package com.benjamin.parsy.ksb.order.usecase.dto;

import java.util.UUID;

public interface IDesiredProductPublicData {

    UUID productUuid();
    int quantity();
    double price();

}
