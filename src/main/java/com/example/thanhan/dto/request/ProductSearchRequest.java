package com.example.thanhan.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchRequest {
    private String productName;
    private String productCode;
    private String productTypeCode;
}
