package pl.kondziet.springbackend.application.port.in.command;

import lombok.Builder;

@Builder
public record RefreshAuthenticationOutcome(String accessToken, String refreshToken) {
}
