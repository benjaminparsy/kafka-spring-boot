package com.benjamin.parsy.runnetic.stock.usecase.publicdata;

import java.util.UUID;

public record TestDesiredProductPublicData(UUID productUuid,
                                           int quantity,
                                           double price) implements IDesiredProductPublicData {
}
