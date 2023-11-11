package pl.kondziet.springbackend.application.domain.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.application.domain.model.Group;
import pl.kondziet.springbackend.application.domain.model.GroupMembership;
import pl.kondziet.springbackend.application.port.in.CreateGroupCommand;
import pl.kondziet.springbackend.application.port.in.CreateGroupUseCase;
import pl.kondziet.springbackend.application.port.out.SaveGroupMembershipPort;
import pl.kondziet.springbackend.application.port.out.SaveGroupPort;

@RequiredArgsConstructor
@Service
@Transactional
public class CreateGroupService implements CreateGroupUseCase {

    private final SaveGroupPort saveGroupPort;
    private final SaveGroupMembershipPort saveGroupMembershipPort;
    @Override
    public void createGroup(CreateGroupCommand command) {
        Group group = Group.builder()
                .name(command.groupName())
                .currency(command.groupCurrency())
                .ownerId(command.groupOwnerId())
                .build();

        Group savedGroup = saveGroupPort.saveGroup(group);

        GroupMembership groupMembership = GroupMembership.builder()
                .userId(command.groupOwnerId())
                .groupId(savedGroup.getId())
                .build();

        saveGroupMembershipPort.saveGroupMembership(groupMembership);
    }
}
