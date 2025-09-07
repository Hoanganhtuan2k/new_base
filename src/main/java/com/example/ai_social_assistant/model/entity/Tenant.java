package com.example.ai_social_assistant.model.entity;


import com.example.ai_social_assistant.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tenants")
public class Tenant extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    private String plan; // Starter, Pro, Business
}