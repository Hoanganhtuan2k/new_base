package com.example.ai_social_assistant.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseEntityRequest {
    @NotNull
    private Long id;

}
