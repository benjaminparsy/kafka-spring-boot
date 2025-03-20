package com.benjamin.parsy.runnetic.stock.usecase.dto;

import java.util.UUID;

public interface IDesiredProductPublicData {

    UUID productUuid();
    int quantity();
    double price();

}
