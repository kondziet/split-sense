package pl.kondziet.springbackend.adapter.out.persistence.repository;

import pl.kondziet.springbackend.application.domain.model.entity.GroupMembership;

public interface GroupMembershipRepository {

    void save(GroupMembership groupMembership);
}
