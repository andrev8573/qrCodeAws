package com.andre.qrcode.generator.controller;

import com.andre.qrcode.generator.dto.QrCodeGenerateRequest;
import com.andre.qrcode.generator.dto.QrCodeGenerateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qrcode")
public class QrCodeController {
    @PostMapping
    public ResponseEntity<QrCodeGenerateResponse> generate(@RequestBody QrCodeGenerateRequest request){ // DTOs da requisição em @RequestBody e da resposta parametrizada dentro de ResponseEntity
        return null;
    }
}
