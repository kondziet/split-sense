package pl.kondziet.springbackend.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.kondziet.springbackend.adapter.out.persistence.entity.DebtJpaEntity;
import pl.kondziet.springbackend.adapter.out.persistence.entity.GroupExpenseJpaEntity;
import pl.kondziet.springbackend.application.domain.model.entity.Debt;
import pl.kondziet.springbackend.application.domain.model.entity.GroupExpense;
import pl.kondziet.springbackend.application.domain.model.entity.Money;
import pl.kondziet.springbackend.application.domain.model.id.ExpenseId;
import pl.kondziet.springbackend.application.domain.model.id.GroupId;
import pl.kondziet.springbackend.application.domain.model.id.UserId;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    List<GroupExpense> groupExpenseJpaEntitiesToGroupExpenses(List<GroupExpenseJpaEntity> groupJpaEntities);

    @Mapping(target = "id", source = "groupExpenseJpaEntity.id", qualifiedByName = "idToExpenseId")
    @Mapping(target = "creditorId", source = "groupExpenseJpaEntity.creditor.id", qualifiedByName = "idToUserId")
    @Mapping(target = "debts", source = "groupExpenseJpaEntity.debtJpaEntities", qualifiedByName = "debtJpaEntityToDebt")
    @Mapping(target = "groupId", source = "groupExpenseJpaEntity.groupJpaEntity.id", qualifiedByName = "idToGroupId")
    GroupExpense groupExpenseJpaEntityToGroupExpense(GroupExpenseJpaEntity groupExpenseJpaEntity);

    @Named("idToExpenseId")
    default ExpenseId idToExpenseId(UUID id) {
        return new ExpenseId(id);
    }

    @Named("idToUserId")
    default UserId idToUserId(UUID id) {
        return new UserId(id);
    }
    @Named("debtJpaEntityToDebt")
    default Debt debtJpaEntityToDebt(DebtJpaEntity debtJpaEntity) {
        return new Debt(
                new UserId(debtJpaEntity.getDebtor().getId()),
                new Money(
                        debtJpaEntity.getMoney().getCurrency(),
                        debtJpaEntity.getMoney().getAmount()
                )
        );
    }
    @Named("idToGroupId")
    default GroupId idToGroupId(UUID id) {
        return new GroupId(id);
    }
}
