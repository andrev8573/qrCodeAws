package com.andre.qrcode.generator.dto;

// DTO responsável por trazer os dados do body na geração de um qr code
public record QrCodeGenerateRequest(String text) {
}
