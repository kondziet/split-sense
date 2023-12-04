package pl.kondziet.springbackend.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.application.domain.model.entity.*;
import pl.kondziet.springbackend.application.domain.model.id.GroupId;
import pl.kondziet.springbackend.application.port.in.CalculateGroupReimbursementsUseCase;
import pl.kondziet.springbackend.application.port.out.ExpensePersistencePort;
import pl.kondziet.springbackend.application.port.out.GroupPersistencePort;

import java.util.*;

@RequiredArgsConstructor
@Service
public class CalculateGroupReimbursementsService implements CalculateGroupReimbursementsUseCase {

    private final ExpensePersistencePort expensePersistencePort;
    private final GroupPersistencePort groupPersistencePort;
    private final BalanceCalculator balanceCalculator;
    private final ReimbursementCalculator reimbursementCalculator;
    private final ExchangeRateConverter exchangeRateConverter;

    @Override
    public List<Reimbursement> calculateReimbursements(GroupId groupId) {
        Group group = groupPersistencePort.loadGroupById(groupId);

        List<GroupExpense> groupExpenses = expensePersistencePort.loadGroupExpenses(groupId);
        List<GroupExpense> exchangedExpenses = exchangeRateConverter.convert(groupExpenses, group);
        List<Balance> groupBalances = balanceCalculator.calculateGroupBalances(exchangedExpenses, group);

        return reimbursementCalculator.calculateGroupReimbursements(groupBalances, group);
    }

}
