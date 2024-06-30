package org.example.motorbikerental.controller;

import org.example.motorbikerental.entity.Motorbike;
import org.example.motorbikerental.entity.User;
import org.example.motorbikerental.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.Optional;

public class MotorbikeController {
//    @Autowired
//    private JWTService jwtService;

//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    public ResponseEntity<Motorbike> registerMotorbike(@RequestHeader("Authorization") String accessToken, @RequestBody Motorbike motorbike) {
//        String token = accessToken.split(" ")[1];
//        String username = this.jwtService.extractUsername(token);
//        System.out.println(username);
//        Optional<User> user = userRepository.findByEmail(username);
//        if (user.isPresent()) {
//            user.get().setBalance(BigDecimal.valueOf(0.0));
//            motorbike.setUser(user.get());
//        }
//        Optional<Model> optionalModel =  modelRepository.findById(motorbike.getModel().getId());
//
//        if (optionalModel.isPresent()) {
//            motorbike.setModel(optionalModel.get());
//        }
//
//        Motorbike newMotor = motorbikeService.registerMotorbike(motorbike);
//
//        return ResponseEntity.ok(newMotor);
//    }
}

