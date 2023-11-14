package pl.kondziet.springbackend.application.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import pl.kondziet.springbackend.application.domain.model.id.ExpenseId;
import pl.kondziet.springbackend.application.domain.model.id.UserId;

import java.util.Set;

@Data
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class Expense {

    private ExpenseId id;
    private String name;
    private UserId payerId;
    private Set<Debt> debts;
}
