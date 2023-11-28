package pl.kondziet.springbackend.adapter.out.persistence.repository;

import pl.kondziet.springbackend.application.domain.model.entity.Group;
import pl.kondziet.springbackend.application.domain.model.id.GroupId;
import pl.kondziet.springbackend.application.domain.model.id.UserId;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {
    Optional<Group> save(Group group);
    List<Group> findAllUserGroups(UserId userId);
    Optional<Group> findGroupById(GroupId groupId);
}
