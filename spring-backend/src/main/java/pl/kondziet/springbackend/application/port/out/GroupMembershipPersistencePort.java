package pl.kondziet.springbackend.application.port.out;

import pl.kondziet.springbackend.domain.model.entity.GroupMembership;

public interface GroupMembershipPersistencePort {
    void saveGroupMembership(GroupMembership groupMembership);
}
