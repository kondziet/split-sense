package pl.kondziet.springbackend.adapter.out.persistence.repository;

import pl.kondziet.springbackend.application.domain.model.entity.GroupMembership;
import pl.kondziet.springbackend.application.domain.model.entity.User;
import pl.kondziet.springbackend.application.domain.model.id.GroupId;

import java.util.List;

public interface GroupMembershipRepository {

    void save(GroupMembership groupMembership);
    List<User> findGroupMembers(GroupId groupId);
}
