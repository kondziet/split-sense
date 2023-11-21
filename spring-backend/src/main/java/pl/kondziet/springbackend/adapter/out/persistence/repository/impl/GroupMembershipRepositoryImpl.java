package pl.kondziet.springbackend.adapter.out.persistence.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.kondziet.springbackend.adapter.out.persistence.entity.GroupJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.entity.GroupMembershipJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.entity.UserJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.repository.GroupMembershipRepository;
import pl.kondziet.springbackend.application.domain.model.entity.GroupMembership;


@AllArgsConstructor
@Repository
@Transactional
public class GroupMembershipRepositoryImpl implements GroupMembershipRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(GroupMembership groupMembership) {
        GroupMembershipJpaEntity.UserGroupId groupMembershipId = new GroupMembershipJpaEntity.UserGroupId(
                groupMembership.getUserId().id(), groupMembership.getGroupId().id()
        );

        UserJpaEntity userJpaEntity = em.getReference(UserJpaEntity.class, groupMembership.getUserId().id());

        GroupJpaEntity groupJpaEntity = em.getReference(GroupJpaEntity.class, groupMembership.getGroupId().id());

        GroupMembershipJpaEntity groupMembershipJpaEntity = GroupMembershipJpaEntity.builder()
                .id(groupMembershipId)
                .userJpaEntity(userJpaEntity)
                .groupJpaEntity(groupJpaEntity)
                .build();

        em.persist(groupMembershipJpaEntity);
    }
}
