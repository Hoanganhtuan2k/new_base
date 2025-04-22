package com.example.motel.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseEntityRequest {
    @NotNull
    private Long id;

}
