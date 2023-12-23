package pl.kondziet.springbackend.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kondziet.springbackend.domain.model.entity.GroupMembership;

import java.util.UUID;

public interface GroupMembershipRepository extends JpaRepository<GroupMembership, UUID> {
}
