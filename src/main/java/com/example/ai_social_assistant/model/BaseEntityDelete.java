package com.example.ai_social_assistant.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class BaseEntityDelete extends BaseEntity {
    @Column(name = "is_delete", updatable = false)
    private Boolean isDelete;
}
