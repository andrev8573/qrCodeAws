package com.andre.qrcode.generator.controller;

import com.andre.qrcode.generator.dto.QrCodeGenerateRequest;
import com.andre.qrcode.generator.dto.QrCodeGenerateResponse;
import com.andre.qrcode.generator.service.QrCodeGeneratorService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qrcode")
public class QrCodeController {
    private final QrCodeGeneratorService qrCodeGeneratorService;

    public QrCodeController(QrCodeGeneratorService qrCodeService) {
        this.qrCodeGeneratorService = qrCodeService;
    }


    // Os dados do body, através da dto de requisição, são pegos e postos no metodo, que implementa a logica atraves do service, que vai transformar o texto da requisição em uma matriz de bits usando a lib do Google e torná-la em um qr code de maneira a poder subi-lo para o banco S3 dentro da infraestrutura AWS (implementada nos pacotes infra e ports) e retornando uma url na mesma através da dto de retorno
    @PostMapping
    public ResponseEntity<QrCodeGenerateResponse> generate(@RequestBody QrCodeGenerateRequest request) { // DTOs da requisição em @RequestBody e da resposta parametrizada dentro de ResponseEntity
        try {
            QrCodeGenerateResponse response = this.qrCodeGeneratorService.generateAndUploadQrCode(request.text());
            return ResponseEntity.ok(response);

        } catch (Exception e) { // Exceção do metodo e/ou informações da requisição
            return ResponseEntity.internalServerError().build();
        }
    }
}
