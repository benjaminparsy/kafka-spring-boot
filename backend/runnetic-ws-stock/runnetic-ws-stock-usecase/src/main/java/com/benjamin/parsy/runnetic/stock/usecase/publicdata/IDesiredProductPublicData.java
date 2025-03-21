package com.benjamin.parsy.runnetic.stock.usecase.publicdata;

import java.util.UUID;

public interface IDesiredProductPublicData {

    UUID productUuid();
    int quantity();
    double price();

}
