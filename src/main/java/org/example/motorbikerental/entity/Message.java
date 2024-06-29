package org.example.motorbikerental.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Column(name = "user_id")
    private Long userId;


    private Date timestamp;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    private String room;

    @PrePersist
    protected void onCreate() {
        timestamp = new Date();
    }
}