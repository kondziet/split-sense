package pl.kondziet.springbackend.infrastructure.adapter.persistence.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
    @JoinColumn(name = "group_id")
    private GroupJpaEntity groupJpaEntity;

}
