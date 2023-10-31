package pl.kondziet.springbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpringBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBackendApplication.class, args);
    }

}
