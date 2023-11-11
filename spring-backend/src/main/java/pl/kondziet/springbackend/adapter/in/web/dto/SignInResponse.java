package pl.kondziet.springbackend.adapter.in.web.dto;

import lombok.Builder;

@Builder
public record SignInResponse(String accessToken, String refreshToken) {
}
