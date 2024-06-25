
package org.example.motorbikerental.service.impl;
import org.example.motorbikerental.config.VNPayConfig;
import org.example.motorbikerental.dto.PaymentDto;
import org.example.motorbikerental.entity.Transaction;
import org.example.motorbikerental.entity.User;
import org.example.motorbikerental.repository.TransactionRepository;
import org.example.motorbikerental.service.PaymentService;
import org.example.motorbikerental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {


    @Autowired
    private UserService userService;
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public ResponseEntity<?> createPayment(Long id, double amount) throws UnsupportedEncodingException {
        Optional<User> optionalUser = Optional.ofNullable(userService.getUserById(id));
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = optionalUser.get();
        long amountInVND = (long) (amount * 100);

        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VNPayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VNPayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amountInVND));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl + "?id=" + id + "&amount=" + amount);
        vnp_Params.put("vnp_IpAddr", VNPayConfig.getIpAddress());

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (Iterator<String> itr = fieldNames.iterator(); itr.hasNext();) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;

        Transaction transaction =  new Transaction();
        transaction.setUsers(user);
        transaction.setAmount(amountInVND);
        transaction.setVnpTxnRef(vnp_TxnRef);
        transaction.setProcessed(false);
        transaction.setTransactionDate(new Date());
        transactionRepository.save(transaction);


        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setStatus("OK");
        paymentDto.setMessage("Successfully");
        paymentDto.setUrl(paymentUrl);

        return ResponseEntity.status(HttpStatus.OK).body(paymentDto);
    }

    @Override
    public ResponseEntity<Void> returnPayment(String vnp_ResponseCode, double amount, Long id, String vnp_TxnRef) {
        Optional<Transaction> optionalTransaction = transactionRepository.findByVnpTxnRef(vnp_TxnRef);
        if (!optionalTransaction.isPresent()) {
            String redirectUrl = "http://localhost:3000/payment-failed";
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", redirectUrl)
                    .build();
        }

        Transaction transaction = optionalTransaction.get();

        if (transaction.isProcessed()) {
            String redirectUrl = "http://localhost:3000/wallet";
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", redirectUrl)
                    .build();
        }

        if ("00".equals(vnp_ResponseCode)) {
            userService.updateUserBalance(id, amount);
            transaction.setProcessed(true);
            transaction.setVnpResponseCode(vnp_ResponseCode);
            transactionRepository.save(transaction);

            String redirectUrl = "http://localhost:3000/payment-success";
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", redirectUrl)
                    .build();
        } else {
            transaction.setProcessed(true);
            transaction.setVnpResponseCode(vnp_ResponseCode);
            transactionRepository.save(transaction);

            String redirectUrl = "http://localhost:3000/payment-failed";
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", redirectUrl)
                    .build();
        }
    }
}


