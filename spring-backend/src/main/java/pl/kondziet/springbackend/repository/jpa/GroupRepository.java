package pl.kondziet.springbackend.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kondziet.springbackend.model.entity.Group;
import pl.kondziet.springbackend.model.entity.User;
import pl.kondziet.springbackend.repository.custom.CustomGroupRepository;

import java.util.List;
import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group, UUID>, CustomGroupRepository {

    @Query("""
            SELECT ug.group
            FROM UserGroup ug
            WHERE ug.user = :user
            """)
    List<Group> findAllUserGroupsByEmail(User user);

}
