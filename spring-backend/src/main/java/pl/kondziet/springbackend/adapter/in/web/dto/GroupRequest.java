package pl.kondziet.springbackend.adapter.in.web.dto;

import lombok.Builder;

@Builder
public record GroupRequest(String name, String currency) {
}
