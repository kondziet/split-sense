package pl.kondziet.springbackend.application.service.dto;

import lombok.Builder;

@Builder
public record TokenRefreshResponse(String accessToken, String refreshToken) {
}
