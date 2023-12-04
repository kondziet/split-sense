package pl.kondziet.springbackend.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import pl.kondziet.springbackend.domain.model.valueobjects.ExpenseId;
import pl.kondziet.springbackend.domain.model.valueobjects.UserId;

import java.util.Set;

@Data
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class Expense {

    private ExpenseId id;
    private String name;
    private UserId creditorId;
    private Set<Debt> debts;
}
