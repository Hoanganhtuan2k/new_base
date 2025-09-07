package com.example.ai_social_assistant.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequestDto {
    @NotBlank(message = "Mã sản phẩm không được để trống.")
    private Long dishId;

    @NotNull(message = "Số lượng là bắt buộc nhập")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private Integer quantity;

}
