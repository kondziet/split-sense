package pl.kondziet.springbackend.model.dto;

import lombok.Builder;

@Builder
public record SignInResponse(String accessToken, String refreshToken) {
}
