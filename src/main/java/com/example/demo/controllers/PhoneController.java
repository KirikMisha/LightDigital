package com.example.demo.controllers;

import com.example.demo.client.DaDataClient;
import com.example.demo.models.request.PhoneRequest;
import com.example.demo.models.response.PhoneResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhoneController {
    private final DaDataClient daDataClient;
    private final String authorizationToken;
    private final String secret;

    @Autowired
    public PhoneController(DaDataClient daDataClient,
                           @Value("${authorization.token}") String authorizationToken,
                           @Value("${authorization.secret}") String secret) {
        this.daDataClient = daDataClient;
        this.authorizationToken = authorizationToken;
        this.secret = secret;
    }

    @PostMapping("/check-phone")
    public ResponseEntity<String> checkPhone(@RequestBody PhoneRequest phoneRequest) {
        String phone = phoneRequest.getPhone();
        if (phone == null || phone.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phone number is required.");
        }

        PhoneResponse[] responses = daDataClient.cleanPhone(authorizationToken, secret, new String[]{phone});
        if (responses != null && responses.length > 0) {
            PhoneResponse response = responses[0];
            String countryCode = response.getCountry_code();
            String cityCode = response.getCity_code();
            String phoneNumber = response.getPhone();
            return ResponseEntity.ok("Phone checked successfully. Country Code: " + countryCode + ", City Code: " + cityCode + ", Phone Number: " + phoneNumber);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to check phone.");
        }
    }
}
