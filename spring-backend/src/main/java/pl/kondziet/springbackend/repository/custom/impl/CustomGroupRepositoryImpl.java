package pl.kondziet.springbackend.repository.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.kondziet.springbackend.model.entity.Group;
import pl.kondziet.springbackend.model.entity.User;
import pl.kondziet.springbackend.model.entity.UserGroup;
import pl.kondziet.springbackend.repository.custom.CustomGroupRepository;

@AllArgsConstructor
@Repository
public class CustomGroupRepositoryImpl implements CustomGroupRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Group saveGroupAndAssignOwner(Group group, User user) {
        User mergedUser = entityManager.merge(user);

        group.setOwner(mergedUser);
        entityManager.persist(group);

        UserGroup membership = UserGroup.builder()
                .id(new UserGroup.UserGroupId(mergedUser.getId(), group.getId()))
                .user(mergedUser)
                .group(group)
                .build();

        entityManager.persist(membership);

        return group;
    }
}
