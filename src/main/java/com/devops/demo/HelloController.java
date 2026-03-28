package com.devops.demo;

import org.springframework.web.bind.annotation.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Hello from DevOps ECS Project!";
    }

    @GetMapping("/db-test")
    public String testDB() {
        try {
            Map<String, String> secret = SecretsManagerService.getSecret();

            String host = secret.get("host");
            String username = secret.get("username");
            String password = secret.get("password");
            String dbname = secret.get("dbname");
            String port = secret.get("port");

            // ✅ CLEAN JDBC URL
            String url = "jdbc:mysql://" + host + ":" + port + "/" + dbname +
                         "?useSSL=false&serverTimezone=UTC";

            Connection conn = DriverManager.getConnection(url, username, password);

            if (conn != null && !conn.isClosed()) {
                conn.close(); // ✅ connection close karna good practice hai
                return "✅ DB Connected Successfully!";
            } else {
                return "❌ DB Connection Failed";
            }

        } catch (Exception e) {
            return "❌ Error: " + e.getMessage();
        }
    }
}
