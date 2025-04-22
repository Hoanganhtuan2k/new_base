package com.example.motel.model.entity;

import com.example.motel.model.BaseEntity;
import com.example.motel.model.enumeration.ContractStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ContractModel extends BaseEntity {


  private String studentId;
  private String roomId;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private String finalPrice;
  private String description;

  @Enumerated(EnumType.STRING)
  private ContractStatus status;
}
