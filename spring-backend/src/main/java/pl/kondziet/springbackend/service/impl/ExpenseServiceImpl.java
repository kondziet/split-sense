package pl.kondziet.springbackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.adapter.in.web.dto.DebtRequest;
import pl.kondziet.springbackend.adapter.in.web.dto.PersonalExpenseRequest;
import pl.kondziet.springbackend.adapter.out.persistence.entity.*;
import pl.kondziet.springbackend.adapter.out.persistence.repository.ExpenseRepository;
import pl.kondziet.springbackend.adapter.out.persistence.repository.GroupRepository;
import pl.kondziet.springbackend.adapter.out.persistence.repository.UserRepository;
import pl.kondziet.springbackend.service.ExpenseService;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
//    private final ExpenseMapper expenseMapper;

    @Transactional
    @Override
    public PersonalExpenseJpaEntity createPersonalExpense(PersonalExpenseRequest expenseDetails, UserJpaEntity payer) {

        UserJpaEntity mergedUserJpaEntity = userRepository.save(payer);

        PersonalExpenseJpaEntity expense = PersonalExpenseJpaEntity.builder()
                .name(expenseDetails.name())
                .payer(mergedUserJpaEntity)
                .build();

        PersonalExpenseJpaEntity savedExpense = expenseRepository.save(expense);

        List<DebtJpaEntity> savedDebtJpaEntities = saveDebtsToExpense(expenseDetails.debts());

        savedExpense.getDebtJpaEntities().addAll(savedDebtJpaEntities);

        return savedExpense;
    }

    @Transactional
    private List<DebtJpaEntity> saveDebtsToExpense(List<DebtRequest> debts) {

        return debts.stream()
                .map(debtor -> {
                    UserJpaEntity userJpaEntity = userRepository.findById(debtor.debtorId()).orElseThrow();
                    String currency = debtor.currency();
                    BigDecimal amount = debtor.amount();

                    return DebtJpaEntity.builder()
                            .debtor(userJpaEntity)
                            .currency(currency)
                            .amount(amount)
                            .build();
                })
                .toList();
    }
}
