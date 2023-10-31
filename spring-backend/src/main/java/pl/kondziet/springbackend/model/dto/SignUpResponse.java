package pl.kondziet.springbackend.model.dto;

import lombok.Builder;

@Builder
public record SignUpResponse(String message) {
}
