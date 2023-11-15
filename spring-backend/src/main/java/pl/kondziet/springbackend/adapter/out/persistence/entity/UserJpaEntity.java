package pl.kondziet.springbackend.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "USERS")
public class UserJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String email;
    private String password;
    @OneToMany(mappedBy = "userJpaEntity")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<GroupMembershipJpaEntity> groupMembershipJpaEntities = new HashSet<>();
    @OneToMany
    @JoinTable(
            name = "USER_EXPENSES",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "EXPENSE_ID")
    )
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<ExpenseJpaEntity> expenseJpaEntities = new HashSet<>();
}
