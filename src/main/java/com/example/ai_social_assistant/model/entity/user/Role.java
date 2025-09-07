package com.example.ai_social_assistant.model.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Role {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "RoleName", nullable = false, length = 100)
    private String roleName;

    @Column(name = "RoleCode", nullable = false, length = 20)
    private String roleCode;

}