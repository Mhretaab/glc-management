package com.glc.managment.security;

public class AuthenticationRequestDTO {

    private String principal;
    private String credentials;

    public AuthenticationRequestDTO() {
    }

    public AuthenticationRequestDTO(String principal, String credentials) {
        this.principal = principal;
        this.credentials = credentials;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    @Override
    public String toString() {
        return String.format("principal: %s", this.principal);
    }
}