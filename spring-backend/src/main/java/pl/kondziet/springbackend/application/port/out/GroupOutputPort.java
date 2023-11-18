package pl.kondziet.springbackend.application.port.out;

import pl.kondziet.springbackend.application.domain.model.entity.Group;
import pl.kondziet.springbackend.application.domain.model.entity.GroupMembership;

public interface GroupOutputPort {
    Group saveGroup(Group group);
    void saveGroupMembership(GroupMembership groupMembership);
}
