package com.example.ai_social_assistant.model.entity;

import com.example.ai_social_assistant.model.BaseEntity;
import com.example.ai_social_assistant.model.enumeration.ChannelType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "channels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Channel extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private ChannelType type;

    @Column(name = "page_id", nullable = false, length = 255)
    private String pageId;

    @Column(name = "access_token", length = 4000)
    private String accessToken;

    @Column(name = "token_expiry")
    private LocalDateTime tokenExpiry;
}
