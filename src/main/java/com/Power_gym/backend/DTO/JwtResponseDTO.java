package com.Power_gym.backend.DTO;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponseDTO {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    private List<String> privilegeIds;

    public JwtResponseDTO(String accessToken, Long id, String username, String email, List<String> roles, List<String> privilegeIds) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.privilegeIds = privilegeIds;
    }
}
