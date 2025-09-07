package com.example.ai_social_assistant.model.entity;

import com.example.ai_social_assistant.model.BaseEntity;
import com.example.ai_social_assistant.model.enumeration.Sentiment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "interactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Interaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", nullable = false)
    private Channel channel;

    @Column(nullable = false)
    private String externalId; // Comment ID, Message ID

    private String author;

    @Lob
    private String text;

    @Enumerated(EnumType.STRING)
    private Sentiment sentiment;

    private LocalDateTime receivedAt;
}
