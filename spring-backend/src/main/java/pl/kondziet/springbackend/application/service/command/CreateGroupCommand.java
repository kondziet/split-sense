package pl.kondziet.springbackend.application.service.command;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateGroupCommand(String groupName, String groupCurrency, UUID groupOwnerId) {
}
