package com.example.thanhan.model.enumeration;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    // Các trạng thái của đơn hàng
    PENDING("Đang Làm"),
    COMPLETED("Đã Làm Xong"),
    CANCELLED("Đã Hủy");

    private final String description;

    // Constructor để gán mô tả cho mỗi trạng thái
    OrderStatusEnum(String description) {
        this.description = description;
    }
}
