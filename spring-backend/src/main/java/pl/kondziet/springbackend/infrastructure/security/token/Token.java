package pl.kondziet.springbackend.infrastructure.security.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class Token {
    
    private UUID id;
    private TokenType type;
    private boolean expired;
    private boolean revoked;
    private String content;
}
