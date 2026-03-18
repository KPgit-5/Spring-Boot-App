package com.devops.demo;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

@GetMapping("/")
public String hello() {
return "Hello from DevOps ECS Project!";
}

}
