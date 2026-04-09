package com.smartcity.dto.auth;

public class AuthResponse {
  private String accessToken;
  private String tokenType = "Bearer";
  private String role;

  public AuthResponse(String accessToken, String role) {
    this.accessToken = accessToken;
    this.role = role;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public String getTokenType() {
    return tokenType;
  }

  public String getRole() {
    return role;
  }
}

