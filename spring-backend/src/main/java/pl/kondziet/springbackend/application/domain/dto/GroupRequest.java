package pl.kondziet.springbackend.application.domain.dto;

import lombok.Builder;

@Builder
public record GroupRequest(String name, String currency) {
}
