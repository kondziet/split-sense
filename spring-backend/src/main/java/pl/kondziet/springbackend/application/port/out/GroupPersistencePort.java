package pl.kondziet.springbackend.application.port.out;

import pl.kondziet.springbackend.domain.model.entity.Group;
import pl.kondziet.springbackend.domain.model.valueobjects.GroupId;
import pl.kondziet.springbackend.domain.model.valueobjects.UserId;

import java.util.List;

public interface GroupPersistencePort {
    Group saveGroup(Group group);
    List<Group> loadUserGroups(UserId userId);
    Group loadGroupById(GroupId groupId);
}

