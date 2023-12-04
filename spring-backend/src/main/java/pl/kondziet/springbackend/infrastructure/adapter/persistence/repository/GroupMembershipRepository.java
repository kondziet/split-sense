package pl.kondziet.springbackend.infrastructure.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kondziet.springbackend.infrastructure.adapter.persistence.entity.GroupMembershipJpaEntity;

import java.util.UUID;

public interface GroupMembershipRepository extends JpaRepository<GroupMembershipJpaEntity, UUID> {
}
