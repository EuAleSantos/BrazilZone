package br.com.todolistproject.todolist.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

@Service
public class S3Service {

    // Método para fazer upload de arquivos para o S3 usando byte array
    public String uploadToS3(byte[] fileBytes, String fileName) {
        try {
            // Configuração do cliente S3
            S3Client s3Client = S3Client.builder()
                    .region(software.amazon.awssdk.regions.Region.US_EAST_2)
                    .credentialsProvider(StaticCredentialsProvider.create(
                            AwsBasicCredentials.create("Chave da awl", "codigo de segurança da aws")))
                    .build();

            // Criação do objeto de requisição para o upload
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket("xnxxbucket")  // Nome do seu bucket S3
                    .key(fileName)  // Nome do arquivo
                    .build();

            // Enviar o arquivo para o S3
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileBytes));

            // Retornar a URL do arquivo no S3
            return "https://xnxxbucket.s3.amazonaws.com/" + fileName;

        } catch (Exception e) {
            e.printStackTrace();
            return null;  // Retorna null em caso de erro
        }
    }
}
