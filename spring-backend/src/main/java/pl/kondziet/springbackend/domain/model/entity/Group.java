package pl.kondziet.springbackend.domain.model.entity;

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
public class Group implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String currency;
    @ManyToOne
    private User owner;

    @OneToMany(mappedBy = "group")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<GroupMembership> memberships = new HashSet<>();
    @OneToMany(mappedBy = "group")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<GroupExpense> expenses = new HashSet<>();

}
