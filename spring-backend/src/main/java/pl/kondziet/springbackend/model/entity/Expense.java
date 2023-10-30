package pl.kondziet.springbackend.model.entity;

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
@Table(name = "EXPENSES")
public class Expense implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @ManyToOne
    private User payer;
    @ManyToOne
    private Group group;
    @OneToMany(mappedBy = "expense")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<ExpenseDebtor> expenseDebtors = new HashSet<>();

}
