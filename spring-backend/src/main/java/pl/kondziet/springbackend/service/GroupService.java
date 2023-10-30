package pl.kondziet.springbackend.service;

import pl.kondziet.springbackend.model.entity.Group;
import pl.kondziet.springbackend.model.entity.User;

import java.util.List;

public interface GroupService {

    Group save(Group user);
    List<Group> findAllUserGroups(User user);
}
