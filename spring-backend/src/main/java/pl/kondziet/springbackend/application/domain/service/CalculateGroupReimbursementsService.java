package pl.kondziet.springbackend.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.application.domain.model.entity.*;
import pl.kondziet.springbackend.application.domain.model.id.GroupId;
import pl.kondziet.springbackend.application.port.in.CalculateGroupReimbursementsUseCase;
import pl.kondziet.springbackend.application.port.out.ExpenseInputPort;
import pl.kondziet.springbackend.application.port.out.GroupInputPort;

import java.util.*;

@RequiredArgsConstructor
@Service
public class CalculateGroupReimbursementsService implements CalculateGroupReimbursementsUseCase {

    private final ExpenseInputPort expenseInputPort;
    private final GroupInputPort groupInputPort;
    private final BalanceCalculator balanceCalculator;
    private final ReimbursementCalculator reimbursementCalculator;
    private final ExchangeRateConverter exchangeRateConverter;

    @Override
    public List<Reimbursement> calculateReimbursements(GroupId groupId) {
        Group group = groupInputPort.loadGroupById(groupId);

        List<GroupExpense> groupExpenses = expenseInputPort.loadGroupExpenses(groupId);
        List<GroupExpense> exchangedExpenses = exchangeRateConverter.convert(groupExpenses, group);
        List<Balance> groupBalances = balanceCalculator.calculateGroupBalances(exchangedExpenses, group);

        return reimbursementCalculator.calculateGroupReimbursements(groupBalances, group);
    }

}
