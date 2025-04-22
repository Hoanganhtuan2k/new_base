package com.example.motel.dto.response;
import com.example.motel.dto.DishCategoryDto;
import com.example.motel.dto.DishDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuResponse extends DishCategoryDto {
    private List<DishDto> dish;
}
