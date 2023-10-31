package pl.kondziet.springbackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.model.entity.Group;
import pl.kondziet.springbackend.model.entity.User;
import pl.kondziet.springbackend.model.entity.UserGroup;
import pl.kondziet.springbackend.repository.GroupRepository;
import pl.kondziet.springbackend.repository.UserGroupRepository;
import pl.kondziet.springbackend.service.GroupService;

import java.util.List;

@AllArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;

    @Override
    public Group save(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public List<Group> findAllUserGroups(User user) {
        return groupRepository.findAllUserGroupsByEmail(user);
    }

    @Transactional
    @Override
    public Group createNewGroup(Group group, User owner) {

        group.setOwner(owner);
        Group savedGroup = save(group);

        UserGroup userGroup = UserGroup.builder()
                .id(UserGroup.UserGroupId.builder()
                        .userId(owner.getId())
                        .groupId(group.getId())
                        .build()
                )
                .user(owner)
                .group(group)
                .build();

        userGroupRepository.save(userGroup);

        return savedGroup;
    }


}
