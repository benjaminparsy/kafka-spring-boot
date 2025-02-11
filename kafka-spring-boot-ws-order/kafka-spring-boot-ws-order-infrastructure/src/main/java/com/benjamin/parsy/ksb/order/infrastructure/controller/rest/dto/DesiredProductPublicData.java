package com.benjamin.parsy.ksb.order.infrastructure.controller.rest.dto;

import com.benjamin.parsy.ksb.order.usecase.dto.IDesiredProductPublicData;

import java.util.UUID;

public record DesiredProductPublicData(UUID productUuid,
                                      int quantity,
                                      double price) implements IDesiredProductPublicData {}
