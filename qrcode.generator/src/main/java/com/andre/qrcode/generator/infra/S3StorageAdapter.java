package com.andre.qrcode.generator.infra;
import com.andre.qrcode.generator.ports.StoragePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

// Essa classe implementa a lógica de acesso ao S3 a partir da classe StoragePort
@Component // Para ser possível fazer uma injeção de dependência no service
public class S3StorageAdapter implements StoragePort {
    private final S3Client s3Client; // Armazena dados como objetos em buckets
    private final String bucketName; // Diretórios dos buckets
    private final String region; // Cada storage possui uma região no mundo

    // @Value pega os valores do application.properties e, nesse contexto, insere como parâmetro do construtor
    public S3StorageAdapter(@Value("${aws.s3.region}") String region,
                            @Value("${aws.s3.bucket-name}") String bucketName) {
        this.bucketName = bucketName;
        this.region = region;
        this.s3Client = S3Client.builder() // Build da aplicação
                .region(Region.of(this.region))
                .build();
    }

    // Metodo responsavel por enviar o arquivo para o S3 via request
    @Override
    public String uploadFile(byte[] fileData, String fileName, String contentType) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder() // Monta a estrutura de uma requisição
                .bucket(bucketName)
                .key(fileName) // Chave para o acesso das operações no banco
                .contentType(contentType)
                .build();
        // Efetivamente faz a requisição
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileData));
        // Retorno para acessar URL da AWS na web
        return String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucketName, region, fileName);
    }
}
