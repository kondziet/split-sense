package pl.kondziet.springbackend.application.port.in.command;

import lombok.Builder;

@Builder
public record AuthenticateCommand(String email, String password) {
}
