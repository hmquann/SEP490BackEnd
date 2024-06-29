package org.example.motorbikerental.dto;


import lombok.Data;
import org.example.motorbikerental.entity.User;

import java.math.BigDecimal;
import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtAuthenticationResponse {

    private String token;

    private String refreshToken;
    private String email;

    private String phone;

    private List<String> roles;

    private Long id;

    private BigDecimal balance;

    private String firstName;

    private String lastName;

    private boolean isGender;



}
