package pl.kondziet.springbackend.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kondziet.springbackend.adapter.out.persistence.entity.GroupJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.repository.GroupMembershipRepository;
import pl.kondziet.springbackend.adapter.out.persistence.repository.GroupRepository;
import pl.kondziet.springbackend.application.domain.model.entity.Group;
import pl.kondziet.springbackend.application.domain.model.entity.GroupMembership;
import pl.kondziet.springbackend.application.domain.model.id.UserId;
import pl.kondziet.springbackend.application.port.out.GroupInputPort;
import pl.kondziet.springbackend.application.port.out.GroupOutputPort;
import pl.kondziet.springbackend.infrastructure.mapper.GroupMapper;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class GroupPersistenceAdapter implements GroupOutputPort, GroupInputPort {

    private final GroupRepository groupRepository;
    private final GroupMembershipRepository groupMembershipRepository;
    private final GroupMapper groupMapper;

    @Override
    public Group saveGroup(Group group) {
        GroupJpaEntity savedGroup = groupRepository.save(group);
        return groupMapper.groupJpaEntityToGroup(savedGroup);
    }

    @Override
    public Set<Group> loadUserGroups(UserId userId) {
        return groupMapper.groupJpaEntitiesToGroups(groupMembershipRepository.findAllUserGroups(userId.id()));
    }
}
