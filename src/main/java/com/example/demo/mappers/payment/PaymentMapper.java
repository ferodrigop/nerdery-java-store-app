package com.example.demo.mappers.payment;

import com.example.demo.dtos.payment.PaymentDto;
import com.example.demo.entities.payment.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {
    @Mapping(target = "id", ignore = true)
    Payment toEntity(PaymentDto paymentDto);

    @Mapping(target = "orderId", source = "order.id")
    PaymentDto toDto(Payment payment);
}