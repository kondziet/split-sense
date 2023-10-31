package pl.kondziet.springbackend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pl.kondziet.springbackend.model.dto.GroupRequest;
import pl.kondziet.springbackend.model.entity.Group;
import pl.kondziet.springbackend.model.entity.User;
import pl.kondziet.springbackend.repository.jpa.GroupRepository;
import pl.kondziet.springbackend.service.GroupService;
import pl.kondziet.springbackend.util.mapper.GroupMapper;

import java.util.List;

@AllArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Override
    public List<Group> findAllUserGroups(User user) {
        return groupRepository.findAllUserGroupsByEmail(user);
    }

    @Override
    public Group createGroup(GroupRequest groupRequest, User owner) {
        Assert.notNull(owner, "Owner must not be null");

        Group group = groupMapper.dtoToGroup(groupRequest);
        Assert.notNull(group, "Group must not be null");

        return groupRepository.saveGroupAndAssignOwner(group, owner);
    }

}
