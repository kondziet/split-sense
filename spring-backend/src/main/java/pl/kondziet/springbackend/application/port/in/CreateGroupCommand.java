package pl.kondziet.springbackend.application.port.in;

import lombok.Builder;
import pl.kondziet.springbackend.application.domain.model.UserId;

@Builder
public record CreateGroupCommand(String groupName, String groupCurrency, UserId groupOwnerId) {
}
