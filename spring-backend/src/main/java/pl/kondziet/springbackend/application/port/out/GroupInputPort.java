package pl.kondziet.springbackend.application.port.out;

import pl.kondziet.springbackend.application.domain.model.entity.Group;
import pl.kondziet.springbackend.application.domain.model.id.UserId;

import java.util.List;
import java.util.Set;

public interface GroupInputPort {
    List<Group> loadUserGroups(UserId userId);
}

