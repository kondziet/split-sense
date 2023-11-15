package pl.kondziet.springbackend.application.port.in;

import org.springframework.security.core.userdetails.UserDetails;

public interface GenerateTokenUseCase {

    public String generateAccessToken(UserDetails userDetails);
    public String generateRefreshToken(UserDetails userDetails);
}
