package pl.kondziet.springbackend.infrastructure.adapter.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "GROUP_MEMBERSHIPS")
public class GroupMembershipJpaEntity implements Serializable {

    @Data
    @Embeddable
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserGroupId implements Serializable{
        private UUID userId;
        private UUID groupId;
    }

    @EmbeddedId
    private UserGroupId id;
    @ManyToOne
    @MapsId(value = "userId")
    @JoinColumn(name = "USER_ID")
    private UserJpaEntity userJpaEntity;
    @ManyToOne
    @MapsId(value = "groupId")
    @JoinColumn(name = "GROUP_ID")
    private GroupJpaEntity groupJpaEntity;

}
