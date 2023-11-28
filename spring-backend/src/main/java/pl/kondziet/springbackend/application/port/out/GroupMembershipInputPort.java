package pl.kondziet.springbackend.application.port.out;

import pl.kondziet.springbackend.application.domain.model.entity.User;
import pl.kondziet.springbackend.application.domain.model.id.GroupId;

import java.util.List;

public interface GroupMembershipInputPort {

    List<User> loadGroupMembers(GroupId groupId);
}
