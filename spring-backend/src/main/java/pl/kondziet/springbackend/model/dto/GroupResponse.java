package pl.kondziet.springbackend.model.dto;

import lombok.Builder;

@Builder
public record GroupResponse(String name, String currency) {
}
