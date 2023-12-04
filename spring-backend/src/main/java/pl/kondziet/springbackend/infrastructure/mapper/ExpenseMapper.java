package pl.kondziet.springbackend.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.kondziet.springbackend.domain.model.entity.Debt;
import pl.kondziet.springbackend.domain.model.entity.GroupExpense;
import pl.kondziet.springbackend.domain.model.entity.Money;
import pl.kondziet.springbackend.domain.model.entity.PersonalExpense;
import pl.kondziet.springbackend.domain.model.valueobjects.ExpenseId;
import pl.kondziet.springbackend.domain.model.valueobjects.GroupId;
import pl.kondziet.springbackend.domain.model.valueobjects.UserId;
import pl.kondziet.springbackend.infrastructure.adapter.persistence.entity.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

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

    List<GroupExpense> groupExpenseJpaEntitiesToGroupExpenses(List<GroupExpenseJpaEntity> groupExpenseJpaEntities);

    @Mapping(target = "id", source = "personalExpense.id", qualifiedByName = "expenseIdToUuid")
    @Mapping(target = "creditor", source = "personalExpense.creditorId", qualifiedByName = "userIdToUserJpaEntity")
    @Mapping(target = "debtJpaEntities", source = "personalExpense.debts", qualifiedByName = "debtsToDebtJpaEntities")
    PersonalExpenseJpaEntity personalExpenseToPersonalExpenseJpaEntity(PersonalExpense personalExpense);

    @Mapping(target = "id", source = "groupExpense.id", qualifiedByName = "expenseIdToUuid")
    @Mapping(target = "creditor", source = "groupExpense.creditorId", qualifiedByName = "userIdToUserJpaEntity")
    @Mapping(target = "debtJpaEntities", source = "groupExpense.debts", qualifiedByName = "debtsToDebtJpaEntities")
    @Mapping(target = "groupJpaEntity", source = "groupExpense.groupId", qualifiedByName = "groupIdToGroupJpaEntity")
    GroupExpenseJpaEntity groupExpenseToGroupExpenseJpaEntity(GroupExpense groupExpense);

    @Named("expenseIdToUuid")
    default UUID expenseIdToUuid(ExpenseId expenseId) {
        return expenseId != null ? expenseId.id() : null;
    }

    @Named("userIdToUserJpaEntity")
    default UserJpaEntity userIdToUserJpaEntity(UserId userId) {
        return UserJpaEntity.builder()
                .id(userId.id())
                .build();
    }

    @Named("debtsToDebtJpaEntities")
    default Set<DebtJpaEntity> debtsToDebtJpaEntities(Set<Debt> debts) {
        return debts.stream()
                .map(debt -> DebtJpaEntity.builder()
                        .debtor(
                                UserJpaEntity.builder()
                                        .id(debt.getDebtorId().id())
                                        .build()
                        )
                        .money(
                                MoneyJpaEntity.builder()
                                        .currency(debt.getMoney().getCurrency())
                                        .amount(debt.getMoney().getAmount())
                                        .build()
                        )
                        .build()
                )
                .collect(Collectors.toSet());
    }

    @Named("groupIdToGroupJpaEntity")
    default GroupJpaEntity groupIdToGroupJpaEntity(GroupId groupId) {
        return GroupJpaEntity.builder()
                .id(groupId.id())
                .build();
    }
}
