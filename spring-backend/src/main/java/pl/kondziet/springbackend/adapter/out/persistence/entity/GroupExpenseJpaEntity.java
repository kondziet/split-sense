package pl.kondziet.springbackend.adapter.out.persistence.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@DiscriminatorValue("GROUP_EXPENSE")
public class GroupExpenseJpaEntity extends ExpenseJpaEntity {

    @ManyToOne
    private GroupJpaEntity groupJpaEntity;

}
