package pl.kondziet.springbackend.adapter.out.persistence.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.kondziet.springbackend.adapter.out.persistence.entity.GroupExpenseJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.entity.GroupJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.entity.GroupMembershipJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.entity.UserJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.repository.GroupMembershipRepository;
import pl.kondziet.springbackend.application.domain.model.entity.GroupMembership;
import pl.kondziet.springbackend.application.domain.model.entity.User;
import pl.kondziet.springbackend.application.domain.model.id.GroupId;
import pl.kondziet.springbackend.infrastructure.mapper.UserMapper;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@Repository
@Transactional
public class GroupMembershipRepositoryImpl implements GroupMembershipRepository {

    @PersistenceContext
    private EntityManager em;
    private final UserMapper userMapper;

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

    @Override
    public List<User> findGroupMembers(GroupId groupId) {
        String jpql = """
                SELECT ge.userJpaEntity
                FROM GroupMembershipJpaEntity ge
                WHERE ge.groupJpaEntity.id = :groupId
                """;
        TypedQuery<UserJpaEntity> query = em.createQuery(jpql, UserJpaEntity.class);

        UUID uuid = groupId.id();
        query.setParameter("groupId", uuid);

        List<UserJpaEntity> resultList = query.getResultList();
        return userMapper.userJpaEntityToUsers(resultList);
    }
}
