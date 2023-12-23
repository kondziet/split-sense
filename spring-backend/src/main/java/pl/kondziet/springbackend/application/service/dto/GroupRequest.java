package pl.kondziet.springbackend.application.service.dto;

import lombok.Builder;

@Builder
public record GroupRequest(String name, String currency) {
}
