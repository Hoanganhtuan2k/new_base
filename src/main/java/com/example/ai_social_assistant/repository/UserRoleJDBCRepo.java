package com.example.ai_social_assistant.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRoleJDBCRepo {
    List<String> getRoleNamesByUserId(int userId);
}
