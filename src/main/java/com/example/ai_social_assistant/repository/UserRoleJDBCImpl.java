package com.example.ai_social_assistant.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserRoleJDBCImpl implements UserRoleJDBCRepo {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<String> getRoleNamesByUserId(int userId) {
        String sql = "SELECT r.role_code " +
                "FROM user_role b " +
                "JOIN role r ON b.role_id = r.id " +
                "WHERE b.user_id = ?";
        return jdbcTemplate.queryForList(sql, new Object[]{userId}, String.class);
    }
}
