package com.example.motel.model.entity;

import com.example.motel.model.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StudentModel extends BaseEntity {

  private String fullName;
  private String phone;
  private String email;
  private String currentContractId; // hợp đồng đang có hiệu lực (nếu có)
}
