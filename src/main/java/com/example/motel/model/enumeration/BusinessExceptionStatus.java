package com.example.motel.model.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessExceptionStatus {
    //Auth
    EXPIRED_TOKEN(402, "expired.token"),
    ACCESS_DENIED(406, "access.denied"),
    AUTHENTICATION_FAILED(407, "authentication.failed"),
    INVALID_CREDENTIALS(408, "invalid.credentials"),
    USER_ALREADY_EXISTS(409, "user.already.exists"),
    USER_NOT_FOUND(410, "user.not.found"),
    INVALID_SIGNATURE(411, "invalid.signature"), // Thêm trạng thái mới
    //Dísh
    PRODUCT_NOT_AVAILABLE(310, "product.not.available"),
    //    501 -> 600,
    PRODUCT_COUNT_MISMATCH(501, "product.count.mismatch"),
    ORDER_NOT_FOUND(502, "order.not.found")
;

    private final int code;
    private final String message;

}
