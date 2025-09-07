package com.example.ai_social_assistant.model.enumeration;

import lombok.Getter;

@Getter
public enum PaymentStatusEnum {
    UNPAID("Chưa thanh toán"),
    DEBT("Nợ"),
    PARTIALLY_PAID("Đã thanh toán 1 phần"),
    FULLY_PAID("Đã thanh toán hết");

    private final String status;

    PaymentStatusEnum(String status) {
        this.status = status;
    }
}
