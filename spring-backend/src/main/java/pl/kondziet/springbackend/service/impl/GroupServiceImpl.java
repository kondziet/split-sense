package pl.kondziet.springbackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pl.kondziet.springbackend.model.dto.GroupRequest;
import pl.kondziet.springbackend.model.entity.Group;
import pl.kondziet.springbackend.model.entity.User;
import pl.kondziet.springbackend.model.entity.GroupMembership;
import pl.kondziet.springbackend.repository.GroupRepository;
import pl.kondziet.springbackend.repository.GroupMembershipRepository;
import pl.kondziet.springbackend.repository.UserRepository;
import pl.kondziet.springbackend.service.GroupService;
import pl.kondziet.springbackend.util.mapper.GroupMapper;

import java.util.List;

@AllArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupMembershipRepository groupMembershipRepository;
    private final UserRepository userRepository;
    private final GroupMapper groupMapper;

    @Override
    public List<Group> findAllUserGroups(User user) {
        return groupMembershipRepository.findAllUserGroupsByEmail(user);
    }

    @Transactional
    @Override
    public Group createGroup(GroupRequest groupRequest, User owner) {

        Group group = groupMapper.dtoToGroup(groupRequest);

        Assert.notNull(owner, "Owner must not be null");
        Assert.notNull(group, "Group must not be null");

        User mergedOwner = userRepository.save(owner);

        group.setOwner(mergedOwner);
        Group savedGroup = groupRepository.save(group);

        GroupMembership membership = GroupMembership.builder()
                .id(new GroupMembership.UserGroupId(mergedOwner.getId(), savedGroup.getId()))
                .user(mergedOwner)
                .group(savedGroup)
                .build();

        groupMembershipRepository.save(membership);

        return savedGroup;
    }

}
