package com.benjamin.parsy.ksb.stock.infrastructure.dto;

import com.benjamin.parsy.ksb.stock.usecase.dto.IDesiredProductPublicData;

import java.util.UUID;

public record DesiredProductPublicData(UUID productUuid,
                                       int quantity,
                                       double price) implements IDesiredProductPublicData {}
