package pl.kondziet.springbackend.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.kondziet.springbackend.application.domain.dto.GroupResponse;
import pl.kondziet.springbackend.adapter.out.persistence.entity.GroupJpaEntity;
import pl.kondziet.springbackend.application.domain.model.entity.Group;
import pl.kondziet.springbackend.application.domain.model.id.GroupId;
import pl.kondziet.springbackend.application.domain.model.id.UserId;

import java.util.Set;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    Set<GroupResponse> groupsToGroupResponses(Set<Group> groups);
    GroupResponse groupToGroupResponse(Group group);

    @Mapping(target = "id", source = "groupJpaEntity.id", qualifiedByName = "idToGroupId")
    @Mapping(target = "ownerId", source = "groupJpaEntity.owner.id", qualifiedByName = "idToUserId")
    Group groupJpaEntityToGroup(GroupJpaEntity groupJpaEntity);

    @Named("idToUserId")
    default UserId idToUserId(UUID id) {
        return new UserId(id);
    }

    @Named("idToGroupId")
    default GroupId idToGroupId(UUID id) {
        return new GroupId(id);
    }

    Set<Group> groupJpaEntitiesToGroups(Set<GroupJpaEntity> groupJpaEntities);

}
