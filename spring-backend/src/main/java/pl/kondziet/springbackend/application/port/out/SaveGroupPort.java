package pl.kondziet.springbackend.application.port.out;

import pl.kondziet.springbackend.application.domain.model.Group;

public interface SaveGroupPort {

    Group saveGroup(Group group);
}
