package com.example.motel.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OrderRequestDto {
    private String customerName;
    @NotNull(message = "Số bàn là bắt buộc")
    @Min(value = 1, message = "Số bàn phải lớn hơn 0")
    private Integer tableNum;
    private List<OrderItemRequestDto> items;
}
