package com.project.PatientTracker.payload.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class JwtResponse {
    @Getter @Setter
    private String token;

    @Getter @Setter
    private String type = "Bearer";

    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}