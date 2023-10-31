package pl.kondziet.springbackend.service;

import pl.kondziet.springbackend.model.dto.GroupRequest;
import pl.kondziet.springbackend.model.entity.Group;
import pl.kondziet.springbackend.model.entity.User;

import java.util.List;

public interface GroupService {

    List<Group> findAllUserGroups(User user);
    Group createGroup(GroupRequest groupRequest, User owner);
}
