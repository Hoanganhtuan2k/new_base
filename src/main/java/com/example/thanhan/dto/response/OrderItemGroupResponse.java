package com.example.thanhan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemGroupResponse {
    private Integer no;
    private List<OrderItemResponseDTO> data;
}
