package pl.kondziet.springbackend.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.kondziet.springbackend.infrastructure.adapter.persistence.entity.UserJpaEntity;
import pl.kondziet.springbackend.domain.dto.GroupResponse;
import pl.kondziet.springbackend.infrastructure.adapter.persistence.entity.GroupJpaEntity;
import pl.kondziet.springbackend.domain.model.entity.Group;
import pl.kondziet.springbackend.domain.model.valueobjects.GroupId;
import pl.kondziet.springbackend.domain.model.valueobjects.UserId;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface GroupMapper {


    @Mapping(target = "id", source = "group.id", qualifiedByName = "groupIdToUuid")
    @Mapping(target = "owner", source = "group.ownerId", qualifiedByName = "userIdToUserJpaEntity")
    GroupJpaEntity groupToGroupJpaEntity(Group group);

    @Named("groupIdToUuid")
    default UUID groupIdToUuid(GroupId groupId) {
        return groupId != null ? groupId.id() : null;
    }

    @Named("userIdToUserJpaEntity")
    default UserJpaEntity userIdToUserJpaEntity(UserId userId) {
        return UserJpaEntity.builder()
                .id(userId.id())
                .build();
    }

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

    List<Group> groupJpaEntitiesToGroups(List<GroupJpaEntity> groupJpaEntities);

    GroupResponse groupToGroupResponse(Group group);

    List<GroupResponse> groupsToGroupResponses(List<Group> groups);

}
