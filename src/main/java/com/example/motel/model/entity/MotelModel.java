package com.example.motel.model.entity;

import com.example.motel.model.BaseEntity;
import com.example.motel.model.enumeration.MotelStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MotelModel extends BaseEntity {

  private String name;
  private MotelStatus status; // EMPTY, OCCUPIED
  private String currentContractId; // dùng để lookup contract hiện tại
  private String adminId; // phòng do ai quản lý
  @Transient
  private String adminName;
  private String imageName;
  private Double lat;
  private Double lng;
  private String description;
  private String acreage;
  private String originalPrice;
  private String actualPrice;

}
