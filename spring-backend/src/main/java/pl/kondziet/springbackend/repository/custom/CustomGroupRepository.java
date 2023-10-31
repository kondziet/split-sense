package pl.kondziet.springbackend.repository.custom;

import pl.kondziet.springbackend.model.entity.Group;
import pl.kondziet.springbackend.model.entity.User;

public interface CustomGroupRepository {

    Group saveGroupAndAssignOwner(Group group, User user);
}
