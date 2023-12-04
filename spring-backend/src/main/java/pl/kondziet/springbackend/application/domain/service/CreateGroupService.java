package pl.kondziet.springbackend.application.domain.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.application.domain.model.entity.Group;
import pl.kondziet.springbackend.application.domain.model.entity.GroupMembership;
import pl.kondziet.springbackend.application.port.in.command.CreateGroupCommand;
import pl.kondziet.springbackend.application.port.in.CreateGroupUseCase;
import pl.kondziet.springbackend.application.port.out.GroupMembershipPersistencePort;
import pl.kondziet.springbackend.application.port.out.GroupPersistencePort;

@RequiredArgsConstructor
@Service
@Transactional
public class CreateGroupService implements CreateGroupUseCase {

    private final GroupPersistencePort groupPersistencePort;
    private final GroupMembershipPersistencePort groupMembershipPersistencePort;
    @Override
    public void createGroup(CreateGroupCommand command) {
        Group group = Group.builder()
                .name(command.groupName())
                .currency(command.groupCurrency())
                .ownerId(command.groupOwnerId())
                .build();

        Group savedGroup = groupPersistencePort.saveGroup(group);

        GroupMembership groupMembership = GroupMembership.builder()
                .userId(command.groupOwnerId())
                .groupId(savedGroup.getId())
                .build();

        groupMembershipPersistencePort.saveGroupMembership(groupMembership);
    }
}
