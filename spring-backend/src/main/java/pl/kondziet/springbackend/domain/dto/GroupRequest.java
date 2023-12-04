package pl.kondziet.springbackend.domain.dto;

import lombok.Builder;

@Builder
public record GroupRequest(String name, String currency) {
}
