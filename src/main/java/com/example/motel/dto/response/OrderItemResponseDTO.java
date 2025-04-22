package com.example.motel.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemResponseDTO {
    private String orderCode;
    private String dishName;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalPrice;
    private Integer orderNum;
}
