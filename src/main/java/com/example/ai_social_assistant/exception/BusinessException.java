package com.example.ai_social_assistant.exception;

import com.example.ai_social_assistant.model.enumeration.BusinessExceptionStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Getter
@Setter
public class BusinessException extends RuntimeException {
    private final int status;
    private final String code;
    private final String message;
    private final String url;

    public BusinessException(BusinessExceptionStatus status) {
        super(status.getMessage());
        this.status = status.getCode();
        this.code = status.getMessage();
        this.message = status.getMessage();
        this.url = resolveCurrentRequestUrl();
    }


    private String resolveCurrentRequestUrl() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            return request.getRequestURI(); // hoặc request.getRequestURL().toString() nếu muốn full
        }
        return null;
    }

}
