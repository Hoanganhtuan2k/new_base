package com.example.motel.model.entity;

import com.example.motel.model.BaseEntity;
import com.example.motel.model.enumeration.PostStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PostModel extends BaseEntity {


    private String title;
    private String content;
    private String adminId;          // người đăng bài
    private Long relatedRoomId;    // nếu bài viết liên quan đến 1 phòng cụ thể
    private PostStatus status;


    @Transient
    private MotelModel motelModel;
}
