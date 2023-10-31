package pl.kondziet.springbackend.model.dto;

import lombok.Builder;

@Builder
public record TokenRefreshResponse(String accessToken, String refreshToken) {
}
