package pl.kondziet.springbackend.domain.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "EXPENSES")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "EXPENSE_TYPE")
public abstract class Expense implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @ManyToOne
    private User creditor;

    @ElementCollection
    @CollectionTable(
            name = "EXPENSE_DEBTS",
            joinColumns=@JoinColumn(name = "expense_id", referencedColumnName = "id")
    )
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<Debt> debts = new HashSet<>();

}
