package org.example.motorbikerental.controller;

import org.example.motorbikerental.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/create_payment")
    public ResponseEntity<?> createPayment(@RequestParam Long id, @RequestParam BigDecimal amount) throws UnsupportedEncodingException {
        return paymentService.createPayment(id, amount);
    }

    @GetMapping("/return")
    public ResponseEntity<Void> returnPayment(@RequestParam String vnp_ResponseCode,
                                              @RequestParam BigDecimal amount,
                                              @RequestParam Long id,
                                              @RequestParam String vnp_TxnRef  ) {
        return paymentService.returnPayment(vnp_ResponseCode, amount, id, vnp_TxnRef);
    }
}
