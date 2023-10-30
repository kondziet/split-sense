package pl.kondziet.springbackend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.model.entity.Group;
import pl.kondziet.springbackend.model.entity.User;
import pl.kondziet.springbackend.repository.GroupRepository;
import pl.kondziet.springbackend.service.GroupService;

import java.util.List;

@AllArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Override
    public Group save(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public List<Group> findAllUserGroups(User user) {
        return groupRepository.findAllUserGroupsByEmail(user);
    }


}
