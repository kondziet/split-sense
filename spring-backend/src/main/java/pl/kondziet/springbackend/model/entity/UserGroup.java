package pl.kondziet.springbackend.model.entity;

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
@Table(name = "USER_GROUPS")
public class UserGroup implements Serializable {

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
    private User user;
    @ManyToOne
    @MapsId(value = "groupId")
    private Group group;

}
