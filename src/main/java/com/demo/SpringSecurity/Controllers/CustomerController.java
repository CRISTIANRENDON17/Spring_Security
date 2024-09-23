package com.demo.SpringSecurity.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class CustomerController {

    @GetMapping("/index")
    public String index() {
        return "Hello word!";
    }

    @GetMapping("/index2")
    public String index2() {
        return "Hello word not SECURED";
    }
    @GetMapping("/index3")
    public ResponseEntity<?> index3() {
        String message = "Hello world!"; // Corregido el mensaje
        return ResponseEntity.ok(message);
    }

    @GetMapping("/index4")
    public ResponseEntity<?> index4() {
        String message = "Hello world not SECURED"; // Corregido el mensaje
        return ResponseEntity.ok(message);
    }
}
