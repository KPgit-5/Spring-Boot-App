package com.devops.demo;

import org.springframework.web.bind.annotation.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "final done 14 april";
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
                return "✅ health check b tested good";
            } else {
                return "❌ DB Connection Failed";
            }

        } catch (Exception e) {
            return "❌ Error: " + e.getMessage();
        }
    }
}
