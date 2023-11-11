package pl.kondziet.springbackend.adapter.in.web;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    ResponseEntity<?> hello() {
        return ResponseEntity.ok("hello world :)");
    }
}
