package pl.kondziet.springbackend.adapter.out.persistence.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.kondziet.springbackend.adapter.out.persistence.entity.GroupJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.entity.UserJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.repository.GroupRepository;
import pl.kondziet.springbackend.application.domain.model.entity.Group;
import pl.kondziet.springbackend.application.domain.model.id.UserId;
import pl.kondziet.springbackend.infrastructure.mapper.GroupMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Repository
@Transactional
public class GroupRepositoryImpl implements GroupRepository {

    @PersistenceContext
    private EntityManager em;
    private GroupMapper groupMapper;
    @Override
    public Optional<Group> save(Group group) {
        UserJpaEntity userJpaEntity = em.getReference(UserJpaEntity.class, group.getOwnerId().id());

        GroupJpaEntity groupJpaEntity = GroupJpaEntity.builder()
                .name(group.getName())
                .currency(group.getCurrency())
                .owner(userJpaEntity)
                .build();

        em.persist(groupJpaEntity);

        return Optional.of(groupMapper.groupJpaEntityToGroup(groupJpaEntity));
    }

    @Override
    public List<Group> findAllUserGroups(UserId userId) {
        String jpql = """
                SELECT gm.groupJpaEntity
                            FROM GroupMembershipJpaEntity gm
                            WHERE gm.userJpaEntity.id = :userId
                """;
        TypedQuery<GroupJpaEntity> query = em.createQuery(jpql, GroupJpaEntity.class);

        UUID uuid = userId.id();
        query.setParameter("userId", uuid);

        List<GroupJpaEntity> resultList = query.getResultList();
        return groupMapper.groupJpaEntitiesToGroups(resultList);
    }
}
