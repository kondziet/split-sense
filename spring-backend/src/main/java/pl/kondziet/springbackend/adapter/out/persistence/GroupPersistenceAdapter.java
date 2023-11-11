package pl.kondziet.springbackend.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kondziet.springbackend.adapter.out.persistence.entity.GroupJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.repository.GroupMembershipRepository;
import pl.kondziet.springbackend.adapter.out.persistence.repository.GroupRepository;
import pl.kondziet.springbackend.application.domain.model.Group;
import pl.kondziet.springbackend.application.domain.model.UserId;
import pl.kondziet.springbackend.application.port.out.LoadUserGroupsPort;
import pl.kondziet.springbackend.application.port.out.SaveGroupPort;
import pl.kondziet.springbackend.infrastructure.mapper.GroupMapper;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class GroupPersistenceAdapter implements SaveGroupPort, LoadUserGroupsPort {

    private final GroupRepository groupRepository;
    private final GroupMembershipRepository groupMembershipRepository;
    private final GroupMapper groupMapper;

    @Override
    public Group saveGroup(Group group) {
        GroupJpaEntity savedGroup = groupRepository.save(group);
        return groupMapper.groupJpaEntityToGroup(savedGroup);
    }

    @Override
    public Set<Group> loadGroups(UserId userId) {
        return groupMapper.groupJpaEntitiesToGroups(groupMembershipRepository.findAllUserGroups(userId.id()));
    }
}
