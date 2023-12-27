package pl.kondziet.springbackend.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kondziet.springbackend.domain.model.entity.GroupMembership;

public interface GroupMembershipRepository extends JpaRepository<GroupMembership, GroupMembership.UserGroupId> {
}
