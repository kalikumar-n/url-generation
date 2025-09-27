package com.example.urlgeneration.projections;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "token", "token", "active", "email", "phoneNo" })
public interface UrlTokenProjection {
    String getToken();
    Boolean getActive();
    String getEmail();
    String getPhoneNo();
}
