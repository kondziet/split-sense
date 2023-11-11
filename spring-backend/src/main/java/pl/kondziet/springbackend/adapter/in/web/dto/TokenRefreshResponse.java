package pl.kondziet.springbackend.adapter.in.web.dto;

import lombok.Builder;

@Builder
public record TokenRefreshResponse(String accessToken, String refreshToken) {
}
