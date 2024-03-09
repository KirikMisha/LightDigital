package com.example.demo.client;

import com.example.demo.models.response.PhoneResponse;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
@FeignClient(name = "dadata-client", url = "https://cleaner.dadata.ru/api/v1/")
@EnableFeignClients(basePackages = "com.example.demo.client")
public interface DaDataClient {
    @PostMapping(value = "clean/phone", consumes = "application/json")
    PhoneResponse[] cleanPhone(
            @RequestHeader("Authorization") String authorization,
            @RequestHeader("X-Secret") String secret,
            @RequestBody String[] phones
    );
}

