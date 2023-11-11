package pl.kondziet.springbackend.application.port.out;

import pl.kondziet.springbackend.application.domain.model.Group;
import pl.kondziet.springbackend.application.domain.model.GroupId;
import pl.kondziet.springbackend.application.domain.model.UserId;

import java.util.Set;

public interface LoadUserGroupsPort {

    Set<Group> loadGroups(UserId userId);
}
