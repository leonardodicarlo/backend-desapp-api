package ar.edu.unq.desapp.grupoI.backenddesappapi.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    @JsonProperty(value = "user_id")
    private final Integer userId;

    public JwtResponse(String jwttoken, Integer userId) {
        this.jwttoken = jwttoken;
        this.userId = userId;
    }

    public String getToken() {
        return this.jwttoken;
    }

    public Integer getUserId() {
        return userId;
    }
}