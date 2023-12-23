package pl.kondziet.springbackend.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kondziet.springbackend.domain.model.entity.Group;

import java.util.List;
import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group, UUID> {
    List<Group> findByMemberships_User_Id(UUID userId);
}
