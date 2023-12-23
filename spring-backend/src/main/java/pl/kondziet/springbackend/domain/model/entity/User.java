package pl.kondziet.springbackend.domain.model.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.kondziet.springbackend.infrastructure.security.oauth2.OAuth2Provider;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String email;
    private String password;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private OAuth2Provider provider;
    @OneToMany(mappedBy = "user")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<GroupMembership> groupMemberships = new HashSet<>();

}
