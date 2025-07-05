package com.andre.qrcode.generator.ports;

// Abstração do armazenamento (será implementado especificante pela infraestrutura da AWS em outro pacote)
public interface StoragePort {
   String uploadFile(byte[] fileData, String fileName, String contentType); // O conteúdo é enviado em forma de bytes e pode ser de diferentes tipos (JSON, imagem, áudio, etc)
}
