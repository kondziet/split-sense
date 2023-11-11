package pl.kondziet.springbackend.adapter.out.persistence.repository.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.kondziet.springbackend.adapter.out.persistence.entity.GroupJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.entity.UserJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.repository.custom.CustomGroupRepository;
import pl.kondziet.springbackend.application.domain.model.Group;

@AllArgsConstructor
@Repository
@Transactional
public class CustomGroupRepositoryImpl implements CustomGroupRepository {

    @PersistenceContext
    private EntityManager em;
    @Override
    public GroupJpaEntity save(Group group) {
        UserJpaEntity userJpaEntity = em.getReference(UserJpaEntity.class, group.getOwnerId().id());

        GroupJpaEntity groupJpaEntity = GroupJpaEntity.builder()
                .name(group.getName())
                .currency(group.getCurrency())
                .owner(userJpaEntity)
                .build();

        em.persist(groupJpaEntity);

        return groupJpaEntity;
    }
}
