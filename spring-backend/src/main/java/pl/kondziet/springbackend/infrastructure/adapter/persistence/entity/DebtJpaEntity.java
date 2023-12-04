package pl.kondziet.springbackend.infrastructure.adapter.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class DebtJpaEntity {

    @ManyToOne
    private UserJpaEntity debtor;
    private MoneyJpaEntity money;
}
