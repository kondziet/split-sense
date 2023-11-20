package pl.kondziet.springbackend.application.domain.dto;

import lombok.Builder;

@Builder
public record TokenRefreshResponse(String accessToken, String refreshToken) {
}
