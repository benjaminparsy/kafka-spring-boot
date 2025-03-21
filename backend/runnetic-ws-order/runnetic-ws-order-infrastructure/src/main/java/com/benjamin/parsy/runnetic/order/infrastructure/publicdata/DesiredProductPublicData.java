package com.benjamin.parsy.runnetic.order.infrastructure.publicdata;

import com.benjamin.parsy.runnetic.order.usecase.publicdata.IDesiredProductPublicData;

import java.util.UUID;

public record DesiredProductPublicData(UUID productUuid,
                                       int quantity,
                                       double price) implements IDesiredProductPublicData {}
