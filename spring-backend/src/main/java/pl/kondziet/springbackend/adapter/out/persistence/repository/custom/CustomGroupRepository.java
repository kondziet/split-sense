package pl.kondziet.springbackend.adapter.out.persistence.repository.custom;

import pl.kondziet.springbackend.adapter.out.persistence.entity.GroupJpaEntity;
import pl.kondziet.springbackend.application.domain.model.Group;

public interface CustomGroupRepository {

    GroupJpaEntity save(Group group);
}
