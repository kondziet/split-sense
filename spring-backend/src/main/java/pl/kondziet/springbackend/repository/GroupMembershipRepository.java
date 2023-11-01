package pl.kondziet.springbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kondziet.springbackend.model.entity.Group;
import pl.kondziet.springbackend.model.entity.GroupMembership;
import pl.kondziet.springbackend.model.entity.User;

import java.util.List;

public interface GroupMembershipRepository extends JpaRepository<GroupMembership, GroupMembership.UserGroupId> {

    @Query("""
            SELECT gm.group
            FROM GroupMembership gm
            WHERE gm.user = :user
            """)
    List<Group> findAllUserGroupsByEmail(User user);
}
