package com.andre.qrcode.generator.service;

import com.andre.qrcode.generator.dto.QrCodeGenerateResponse;
import com.andre.qrcode.generator.ports.StoragePort;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

@Service
public class QrCodeGeneratorService { // Service que efetivamente implementa a geração do qr code utilizando infraestrutura AWS
    private final StoragePort storagePort;

    public QrCodeGeneratorService(StoragePort storagePort) {
        this.storagePort = storagePort;
    }

    public QrCodeGenerateResponse generateAndUploadQrCode(String text){
        // A lib do Google, utilizando o objeto abaixo, transforma a string da requisição em uma matriz de bits com formatação de qr code
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);

        // Utilizando a formatação, converte o mapa de bits em imagem png usando uma classe de arrays do Java IO
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

        // Imagem em código é enviada pra AWS com o metodo uploadFile e gera um retorno usando a dto generateAndUploadQrCode
        byte[] pngQrCodeData = pngOutputStream.toByteArray();
        String url = storagePort.uploadFile(pngQrCodeData, UUID.randomUUID().toString(), "image/png"); // UUID gerado automaticamente
        return new generateAndUploadQrCode(url);
    }

}
