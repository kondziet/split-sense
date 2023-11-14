package pl.kondziet.springbackend.application.port.in.command;

import lombok.Builder;

@Builder
public record AuthenticationOutcome(String accessToken, String refreshToken) {
}
