package pl.kondziet.springbackend.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "GROUPS")
public class GroupJpaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String currency;
    @ManyToOne
    private UserJpaEntity owner;

    @OneToMany(mappedBy = "groupJpaEntity")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<GroupMembershipJpaEntity> groupMembershipJpaEntities = new HashSet<>();
    @OneToMany(mappedBy = "groupJpaEntity")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<GroupExpenseJpaEntity> groupExpenses = new HashSet<>();

}
