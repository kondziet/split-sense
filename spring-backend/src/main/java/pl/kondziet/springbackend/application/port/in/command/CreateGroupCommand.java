package pl.kondziet.springbackend.application.port.in.command;

import lombok.Builder;
import pl.kondziet.springbackend.application.domain.model.id.UserId;

@Builder
public record CreateGroupCommand(String groupName, String groupCurrency, UserId groupOwnerId) {
}
