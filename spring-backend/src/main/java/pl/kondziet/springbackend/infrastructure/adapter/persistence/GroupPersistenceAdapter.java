package pl.kondziet.springbackend.infrastructure.adapter.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kondziet.springbackend.infrastructure.adapter.persistence.entity.GroupJpaEntity;
import pl.kondziet.springbackend.infrastructure.adapter.persistence.entity.GroupMembershipJpaEntity;
import pl.kondziet.springbackend.infrastructure.adapter.persistence.repository.GroupMembershipRepository;
import pl.kondziet.springbackend.infrastructure.adapter.persistence.repository.GroupRepository;
import pl.kondziet.springbackend.domain.model.entity.Group;
import pl.kondziet.springbackend.domain.model.entity.GroupMembership;
import pl.kondziet.springbackend.domain.model.valueobjects.GroupId;
import pl.kondziet.springbackend.domain.model.valueobjects.UserId;
import pl.kondziet.springbackend.application.port.out.GroupPersistencePort;
import pl.kondziet.springbackend.application.port.out.GroupMembershipPersistencePort;
import pl.kondziet.springbackend.infrastructure.mapper.GroupMapper;
import pl.kondziet.springbackend.infrastructure.mapper.GroupMembershipMapper;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GroupPersistenceAdapter implements GroupPersistencePort, GroupMembershipPersistencePort {

    private final GroupRepository groupRepository;
    private final GroupMembershipRepository groupMembershipRepository;
    private final GroupMapper groupMapper;
    private final GroupMembershipMapper groupMembershipMapper;

    @Override
    public Group saveGroup(Group group) {
        GroupJpaEntity savedGroup = groupRepository.save(
                groupMapper.groupToGroupJpaEntity(group)
        );

        return groupMapper.groupJpaEntityToGroup(savedGroup);
    }

    @Override
    public List<Group> loadUserGroups(UserId userId) {
        List<GroupJpaEntity> userGroups = groupRepository
                .findByGroupMembershipJpaEntities_UserJpaEntity_Id(userId.id());

        return groupMapper.groupJpaEntitiesToGroups(userGroups);
    }

    @Override
    public Group loadGroupById(GroupId groupId) {
        GroupJpaEntity groupJpaEntity = groupRepository.findById(groupId.id()).orElseThrow();

        return groupMapper.groupJpaEntityToGroup(groupJpaEntity);
    }

    @Override
    public void saveGroupMembership(GroupMembership groupMembership) {
        GroupMembershipJpaEntity groupMembershipJpaEntity = groupMembershipMapper
                .groupMembershipToGroupMembershipJpaEntity(groupMembership);

        groupMembershipRepository.save(groupMembershipJpaEntity);
    }
}
