package pl.kondziet.springbackend.domain.dto;

import lombok.Builder;

@Builder
public record SignUpResponse(String message) {
}
