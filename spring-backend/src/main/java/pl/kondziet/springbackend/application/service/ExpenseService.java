package pl.kondziet.springbackend.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.application.service.command.CreateGroupExpenseCommand;
import pl.kondziet.springbackend.application.service.command.CreatePersonalExpenseCommand;
import pl.kondziet.springbackend.domain.model.entity.*;
import pl.kondziet.springbackend.infrastructure.persistence.ExpenseRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public GroupExpense createGroupExpense(CreateGroupExpenseCommand command) {

        Set<Debt> debts = command.expenseDebts().stream()
                .map(debt -> Debt.builder()
                        .debtor(User.builder()
                                .id(debt.debtorId())
                                .build()
                        )
                        .money(Money.builder()
                                .currency(debt.currency())
                                .amount(debt.amount())
                                .build())
                        .build()
                )
                .collect(Collectors.toSet());

        GroupExpense groupExpense = GroupExpense.builder()
                .name(command.expenseName())
                .creditor(User.builder()
                        .id(command.expensePayer())
                        .build()
                )
                .debts(debts)
                .group(Group.builder()
                        .id(command.expenseGroupId())
                        .build()
                )
                .build();

        return expenseRepository.save(groupExpense);
    }

    public PersonalExpense createPersonalExpense(CreatePersonalExpenseCommand command) {

        Set<Debt> debts = command.expenseDebts().stream()
                .map(debt -> Debt.builder()
                        .debtor(User.builder()
                                .id(debt.debtorId())
                                .build()
                        )
                        .money(Money.builder()
                                .currency(debt.currency())
                                .amount(debt.amount())
                                .build())
                        .build()
                )
                .collect(Collectors.toSet());

        PersonalExpense personalExpense = PersonalExpense.builder()
                .name(command.expenseName())
                .creditor(User.builder()
                        .id(command.expensePayer())
                        .build()
                )
                .debts(debts)
                .build();

        return expenseRepository.save(personalExpense);
    }
}
