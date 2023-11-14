package pl.kondziet.springbackend.adapter.out.persistence.repository.custom;

import pl.kondziet.springbackend.adapter.out.persistence.entity.GroupMembershipJpaEntity;
import pl.kondziet.springbackend.application.domain.model.entity.GroupMembership;

public interface CustomGroupMembershipRepository {

    void save(GroupMembership groupMembership);
}
