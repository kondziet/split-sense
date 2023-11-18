package pl.kondziet.springbackend.application.domain.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.application.domain.model.entity.Group;
import pl.kondziet.springbackend.application.domain.model.entity.GroupMembership;
import pl.kondziet.springbackend.application.port.in.command.CreateGroupCommand;
import pl.kondziet.springbackend.application.port.in.CreateGroupUseCase;
import pl.kondziet.springbackend.application.port.out.GroupOutputPort;
import pl.kondziet.springbackend.application.port.out.GroupMembershipOutputPort;

@RequiredArgsConstructor
@Service
@Transactional
public class CreateGroupService implements CreateGroupUseCase {

    private final GroupOutputPort groupOutputPort;
    private final GroupMembershipOutputPort groupMembershipOutputPort;
    @Override
    public void createGroup(CreateGroupCommand command) {
        Group group = Group.builder()
                .name(command.groupName())
                .currency(command.groupCurrency())
                .ownerId(command.groupOwnerId())
                .build();

        Group savedGroup = groupOutputPort.saveGroup(group);

        GroupMembership groupMembership = GroupMembership.builder()
                .userId(command.groupOwnerId())
                .groupId(savedGroup.getId())
                .build();

        groupMembershipOutputPort.saveGroupMembership(groupMembership);
    }
}
