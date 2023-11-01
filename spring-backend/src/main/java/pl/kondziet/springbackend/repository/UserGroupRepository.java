package pl.kondziet.springbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kondziet.springbackend.model.entity.UserGroup;

import java.util.UUID;

public interface UserGroupRepository extends JpaRepository<UserGroup, UserGroup.UserGroupId> {
}
