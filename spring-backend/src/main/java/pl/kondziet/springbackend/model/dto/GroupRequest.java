package pl.kondziet.springbackend.model.dto;

import lombok.Builder;

@Builder
public record GroupRequest(String name, String currency) {
}
