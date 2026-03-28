package com.devops.demo;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

public class SecretsManagerService {

    public static Map<String, String> getSecret() {

        String secretName = System.getenv("SECRET_NAME");
        String regionEnv = System.getenv("AWS_REGION");

        if (secretName == null || secretName.isEmpty()) {
            throw new RuntimeException("❌ SECRET_NAME environment variable not set");
        }

        Region region = Region.of(regionEnv != null ? regionEnv : "us-east-1");

        try (SecretsManagerClient client = SecretsManagerClient.builder()
                .region(region)
                .build()) {

            GetSecretValueRequest request = GetSecretValueRequest.builder()
                    .secretId(secretName)
                    .build();

            String secretString = client.getSecretValue(request).secretString();

            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(secretString, new TypeReference<Map<String, String>>() {});

        } catch (Exception e) {
            throw new RuntimeException("❌ Error retrieving/parsing secret: " + e.getMessage(), e);
        }
    }
}
