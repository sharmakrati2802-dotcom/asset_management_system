package com.military.assetmanagement.dto;
public record LoginResponse(String token, String username, String role, Long baseId) {}
