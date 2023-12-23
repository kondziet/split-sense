package pl.kondziet.springbackend.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.application.service.command.CreateGroupCommand;
import pl.kondziet.springbackend.domain.model.entity.Group;
import pl.kondziet.springbackend.domain.model.entity.GroupMembership;
import pl.kondziet.springbackend.domain.model.entity.User;
import pl.kondziet.springbackend.infrastructure.persistence.GroupRepository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMembershipService groupMembershipService;

    public List<Group> loadUserGroups(UUID userId) {
        return groupRepository.findByMemberships_User_Id(userId);
    }

    public Group createGroup(CreateGroupCommand command) {
        User user = User.builder()
                .id(command.groupOwnerId())
                .build();

        Group group = groupRepository.save(
                Group.builder()
                        .name(command.groupName())
                        .currency(command.groupCurrency())
                        .owner(user)
                        .build()
        );

        GroupMembership groupMembership = GroupMembership.builder()
                .id(
                        GroupMembership.UserGroupId.builder()
                                .userId(command.groupOwnerId())
                                .groupId(group.getId())
                                .build()
                )
                .user(user)
                .group(group)
                .build();

        groupMembershipService.save(groupMembership);

        return group;
    }
}
