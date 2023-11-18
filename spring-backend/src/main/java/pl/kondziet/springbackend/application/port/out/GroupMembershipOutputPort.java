package pl.kondziet.springbackend.application.port.out;

import pl.kondziet.springbackend.application.domain.model.entity.GroupMembership;

public interface GroupMembershipOutputPort {

    void saveGroupMembership(GroupMembership groupMembership);
}
