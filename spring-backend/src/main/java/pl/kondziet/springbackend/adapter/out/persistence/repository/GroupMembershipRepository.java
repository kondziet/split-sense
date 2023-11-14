package pl.kondziet.springbackend.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kondziet.springbackend.adapter.out.persistence.entity.GroupJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.entity.GroupMembershipJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.repository.custom.CustomGroupMembershipRepository;

import java.util.Set;
import java.util.UUID;

public interface GroupMembershipRepository extends JpaRepository<GroupMembershipJpaEntity, GroupMembershipJpaEntity.UserGroupId>, CustomGroupMembershipRepository {

    @Query("""
            SELECT gm.groupJpaEntity
            FROM GroupMembershipJpaEntity gm
            WHERE gm.userJpaEntity.id = :userId
            """)
    Set<GroupJpaEntity> findAllUserGroups(UUID userId);
}
