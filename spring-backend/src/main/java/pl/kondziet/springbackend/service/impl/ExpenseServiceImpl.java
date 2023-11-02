package pl.kondziet.springbackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.model.dto.GroupExpenseRequest;
import pl.kondziet.springbackend.model.dto.PersonalExpenseRequest;
import pl.kondziet.springbackend.model.entity.*;
import pl.kondziet.springbackend.repository.ExpenseDebtorRepository;
import pl.kondziet.springbackend.repository.ExpenseRepository;
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
    private final UserRepository userRepository;
    private final ExpenseMapper expenseMapper;

    @Override
    public GroupExpense createGroupExpense(GroupExpenseRequest groupExpenseRequest, User payer, UUID groupId) {
        return null;
    }

    @Transactional
    @Override
    public PersonalExpense createPersonalExpense(PersonalExpenseRequest personalExpenseRequest, User payer) {

        User mergedUser = userRepository.save(payer);

        PersonalExpense expense = PersonalExpense.builder()
                .name(personalExpenseRequest.name())
                .payer(mergedUser)
                .build();

        PersonalExpense savedExpense = expenseRepository.save(expense);

        List<ExpenseDebtor> expenseDebtors = personalExpenseRequest.debts()
                .stream()
                .map(debtor -> {
                    User user = userRepository.findById(debtor.debtorId()).orElseThrow();
                    String currency = debtor.currency();
                    BigDecimal amount = debtor.amount();

                    return ExpenseDebtor.builder()
                            .id(new ExpenseDebtor.ExpenseDebtorId(savedExpense.getId(), user.getId()))
                            .debtor(user)
                            .expense(savedExpense)
                            .currency(currency)
                            .amount(amount)
                            .build();
                })
                .toList();

        savedExpense.getExpenseDebtors().addAll(expenseDebtors);
        expenseDebtorRepository.saveAll(expenseDebtors);

        return savedExpense;
    }
}
