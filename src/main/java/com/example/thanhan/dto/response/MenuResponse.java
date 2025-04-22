package com.example.thanhan.dto.response;
import com.example.thanhan.dto.DishCategoryDto;
import com.example.thanhan.dto.DishDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuResponse extends DishCategoryDto {
    private List<DishDto> dish;
}
