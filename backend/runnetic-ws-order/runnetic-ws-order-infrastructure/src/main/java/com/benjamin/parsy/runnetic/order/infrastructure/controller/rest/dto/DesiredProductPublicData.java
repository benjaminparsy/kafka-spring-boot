package com.benjamin.parsy.runnetic.order.infrastructure.controller.rest.dto;

import com.benjamin.parsy.runnetic.order.usecase.dto.IDesiredProductPublicData;

import java.util.UUID;

public record DesiredProductPublicData(UUID productUuid,
                                      int quantity,
                                      double price) implements IDesiredProductPublicData {}
