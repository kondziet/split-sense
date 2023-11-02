package pl.kondziet.springbackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.model.dto.ExpenseDebtorRequest;
import pl.kondziet.springbackend.model.dto.GroupExpenseRequest;
import pl.kondziet.springbackend.model.dto.PersonalExpenseRequest;
import pl.kondziet.springbackend.model.entity.*;
import pl.kondziet.springbackend.repository.ExpenseDebtorRepository;
import pl.kondziet.springbackend.repository.ExpenseRepository;
import pl.kondziet.springbackend.repository.GroupRepository;
import pl.kondziet.springbackend.repository.UserRepository;
import pl.kondziet.springbackend.service.ExpenseService;
import pl.kondziet.springbackend.util.mapper.ExpenseMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseDebtorRepository expenseDebtorRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final ExpenseMapper expenseMapper;

    @Transactional
    @Override
    public GroupExpense createGroupExpense(GroupExpenseRequest expenseDetails, User payer, UUID groupId) {

        User mergedUser = userRepository.save(payer);
        Group group = groupRepository.findById(groupId).orElseThrow();

        GroupExpense expense = GroupExpense.builder()
                .name(expenseDetails.name())
                .payer(mergedUser)
                .group(group)
                .build();

        GroupExpense savedExpense = expenseRepository.save(expense);

        List<ExpenseDebtor> savedDebts = saveDebtsToExpense(savedExpense, expenseDetails.debts());

        savedExpense.getExpenseDebtors().addAll(savedDebts);

        return savedExpense;
    }

    @Transactional
    @Override
    public PersonalExpense createPersonalExpense(PersonalExpenseRequest expenseDetails, User payer) {

        User mergedUser = userRepository.save(payer);

        PersonalExpense expense = PersonalExpense.builder()
                .name(expenseDetails.name())
                .payer(mergedUser)
                .build();

        PersonalExpense savedExpense = expenseRepository.save(expense);

        List<ExpenseDebtor> savedDebts = saveDebtsToExpense(savedExpense, expenseDetails.debts());

        savedExpense.getExpenseDebtors().addAll(savedDebts);

        return savedExpense;
    }

    @Transactional
    private List<ExpenseDebtor> saveDebtsToExpense(Expense targetExpense, List<ExpenseDebtorRequest> debts) {
        List<ExpenseDebtor> expenseDebts = debts.stream()
                .map(debtor -> {
                    User user = userRepository.findById(debtor.debtorId()).orElseThrow();
                    String currency = debtor.currency();
                    BigDecimal amount = debtor.amount();

                    return ExpenseDebtor.builder()
                            .id(new ExpenseDebtor.ExpenseDebtorId(targetExpense.getId(), user.getId()))
                            .debtor(user)
                            .expense(targetExpense)
                            .currency(currency)
                            .amount(amount)
                            .build();
                })
                .toList();

        return expenseDebtorRepository.saveAll(expenseDebts);
    }
}
