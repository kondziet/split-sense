package pl.kondziet.springbackend.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GroupMembership {

    private UserId userId;
    private GroupId groupId;
}
