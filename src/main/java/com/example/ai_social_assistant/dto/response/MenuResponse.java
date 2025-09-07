package com.example.ai_social_assistant.dto.response;
import com.example.ai_social_assistant.dto.DishCategoryDto;
import com.example.ai_social_assistant.dto.DishDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuResponse extends DishCategoryDto {
    private List<DishDto> dish;
}
