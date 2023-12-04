package pl.kondziet.springbackend.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kondziet.springbackend.adapter.out.persistence.entity.GroupJpaEntity;

import java.util.List;
import java.util.UUID;

public interface GroupRepository extends JpaRepository<GroupJpaEntity, UUID> {
    List<GroupJpaEntity> findByGroupMembershipJpaEntities_UserJpaEntity_Id(UUID userId);
}
