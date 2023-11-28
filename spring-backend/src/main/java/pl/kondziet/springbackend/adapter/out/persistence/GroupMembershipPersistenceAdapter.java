package pl.kondziet.springbackend.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kondziet.springbackend.adapter.out.persistence.repository.GroupMembershipRepository;
import pl.kondziet.springbackend.application.domain.model.entity.GroupMembership;
import pl.kondziet.springbackend.application.domain.model.entity.User;
import pl.kondziet.springbackend.application.domain.model.id.GroupId;
import pl.kondziet.springbackend.application.port.out.GroupMembershipInputPort;
import pl.kondziet.springbackend.application.port.out.GroupMembershipOutputPort;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GroupMembershipPersistenceAdapter implements GroupMembershipOutputPort, GroupMembershipInputPort {

    private final GroupMembershipRepository groupMembershipRepository;

    @Override
    public void saveGroupMembership(GroupMembership groupMembership) {
        groupMembershipRepository.save(groupMembership);
    }

    @Override
    public List<User> loadGroupMembers(GroupId groupId) {
        return groupMembershipRepository.findGroupMembers(groupId);
    }
}
