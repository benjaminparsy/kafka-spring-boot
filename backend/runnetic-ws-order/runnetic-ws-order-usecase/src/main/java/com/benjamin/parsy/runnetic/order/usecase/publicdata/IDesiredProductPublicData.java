package com.benjamin.parsy.runnetic.order.usecase.publicdata;

import java.util.UUID;

public interface IDesiredProductPublicData {

    UUID productUuid();
    int quantity();
    double price();

}
