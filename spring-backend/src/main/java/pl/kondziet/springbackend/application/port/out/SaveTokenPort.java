package pl.kondziet.springbackend.application.port.out;

import pl.kondziet.springbackend.infrastructure.security.token.Token;

public interface SaveTokenPort {

    Token saveToken(Token token);
}
