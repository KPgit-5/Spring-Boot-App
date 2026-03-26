package com.devops.demo;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class SecretsManagerService {

    public static Map<String, String> getSecret() {

        String secretName = "kpsecret";

        String regionEnv = System.getenv("AWS_REGION");
        Region region = Region.of(regionEnv != null ? regionEnv : "us-east-1");

        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(region)
                .build();

        GetSecretValueRequest request = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        String secretString = client.getSecretValue(request).secretString();

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(secretString, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing secret", e);
        }
    }
}
