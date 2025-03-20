package com.benjamin.parsy.runnetic.stock.infrastructure.dto;

import com.benjamin.parsy.runnetic.stock.usecase.dto.IDesiredProductPublicData;

import java.util.UUID;

public record DesiredProductPublicData(UUID productUuid,
                                       int quantity,
                                       double price) implements IDesiredProductPublicData {}
