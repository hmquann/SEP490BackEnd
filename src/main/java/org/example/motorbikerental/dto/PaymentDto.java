package org.example.motorbikerental.dto;


import lombok.Data;

@Data
public class PaymentDto {
    private String status;
    private String message;
    private String url;
}
