package pl.kondziet.springbackend.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kondziet.springbackend.adapter.out.persistence.entity.GroupMembershipJpaEntity;

import java.util.UUID;

public interface GroupMembershipRepository extends JpaRepository<GroupMembershipJpaEntity, UUID> {
}
