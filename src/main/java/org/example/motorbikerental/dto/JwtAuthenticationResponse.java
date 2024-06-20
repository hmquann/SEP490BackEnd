package org.example.motorbikerental.dto;


import lombok.Data;
import org.example.motorbikerental.entity.User;

import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtAuthenticationResponse {

    private String token;

    private String refreshToken;

    private List<String> roles;


    private Long id;

    private double balance;

    private String firstName;

    private String lastName;

    private User user;



}
