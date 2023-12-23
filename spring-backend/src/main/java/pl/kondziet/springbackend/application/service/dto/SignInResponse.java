package pl.kondziet.springbackend.application.service.dto;

import lombok.Builder;

@Builder
public record SignInResponse(String accessToken, String refreshToken) {
}
