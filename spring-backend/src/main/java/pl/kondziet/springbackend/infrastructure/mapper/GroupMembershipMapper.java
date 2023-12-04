package pl.kondziet.springbackend.infrastructure.mapper;

import org.mapstruct.Mapper;
import pl.kondziet.springbackend.infrastructure.adapter.persistence.entity.GroupJpaEntity;
import pl.kondziet.springbackend.infrastructure.adapter.persistence.entity.GroupMembershipJpaEntity;
import pl.kondziet.springbackend.infrastructure.adapter.persistence.entity.UserJpaEntity;
import pl.kondziet.springbackend.domain.model.entity.GroupMembership;

@Mapper(componentModel = "spring")
public interface GroupMembershipMapper {

    default GroupMembershipJpaEntity groupMembershipToGroupMembershipJpaEntity(GroupMembership groupMembership) {
        return GroupMembershipJpaEntity.builder()
                .userJpaEntity(
                        UserJpaEntity.builder()
                                .id(groupMembership.getUserId().id())
                                .build()
                )
                .groupJpaEntity(
                        GroupJpaEntity.builder()
                                .id(groupMembership.getGroupId().id())
                                .build()
                )
                .id(GroupMembershipJpaEntity.UserGroupId.builder()
                        .userId(groupMembership.getUserId().id())
                        .groupId(groupMembership.getGroupId().id())
                        .build())
                .build();
    }
}
