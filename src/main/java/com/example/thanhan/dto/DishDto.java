package com.example.thanhan.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DishDto {
    private Long id;
    private String dishName;
    private String description;
    private BigDecimal price;
    private Long dishCategoryId;
    private Boolean bestSeller;
    private Boolean available;
}
