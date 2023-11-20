package pl.kondziet.springbackend.application.port.out;

import pl.kondziet.springbackend.application.domain.model.entity.Group;

public interface GroupOutputPort {
    Group saveGroup(Group group);
}
