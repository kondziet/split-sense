package pl.kondziet.springbackend.domain.dto;

import lombok.Builder;

@Builder
public record SignInResponse(String accessToken, String refreshToken) {
}
