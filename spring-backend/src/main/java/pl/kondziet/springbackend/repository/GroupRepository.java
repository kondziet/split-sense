package pl.kondziet.springbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kondziet.springbackend.model.entity.Group;
import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group, UUID> {
}
