package pl.kondziet.springbackend.application.port.out;

import pl.kondziet.springbackend.application.domain.model.entity.Group;
import pl.kondziet.springbackend.application.domain.model.entity.User;
import pl.kondziet.springbackend.application.domain.model.id.GroupId;
import pl.kondziet.springbackend.application.domain.model.id.UserId;

import java.util.List;

public interface GroupInputPort {
    List<Group> loadUserGroups(UserId userId);
    Group loadGroupById(GroupId groupId);
}

