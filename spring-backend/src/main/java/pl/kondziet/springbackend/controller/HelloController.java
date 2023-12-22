package pl.kondziet.springbackend.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    String securedHello(Authentication authentication) {
        return authentication.getName();
    }

    @GetMapping("/")
    String unsecuredHello() {
        return "Unsecured hello";
    }
}
