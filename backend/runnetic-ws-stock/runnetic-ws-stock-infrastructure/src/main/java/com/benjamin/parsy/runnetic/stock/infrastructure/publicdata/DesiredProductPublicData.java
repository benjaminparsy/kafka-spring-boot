package com.benjamin.parsy.runnetic.stock.infrastructure.publicdata;

import com.benjamin.parsy.runnetic.stock.usecase.publicdata.IDesiredProductPublicData;

import java.util.UUID;

public record DesiredProductPublicData(UUID productUuid,
                                       int quantity,
                                       double price) implements IDesiredProductPublicData {}
