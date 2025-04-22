package com.example.motel.dto.response.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse implements Serializable {
    @JsonIgnore
    private Integer id;
    private String username;
    private String email;
    private String visibleName;
    private List<String> roles;
    private String accessToken;
}
