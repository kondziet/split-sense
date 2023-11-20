package pl.kondziet.springbackend.application.domain.dto;

import lombok.Builder;

@Builder
public record SignInResponse(String accessToken, String refreshToken) {
}
