package pl.kondziet.springbackend.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kondziet.springbackend.domain.model.entity.Group;
import pl.kondziet.springbackend.domain.model.entity.GroupExpense;
import pl.kondziet.springbackend.domain.model.entity.User;
import pl.kondziet.springbackend.domain.model.valueobjects.Balance;
import pl.kondziet.springbackend.domain.model.valueobjects.Reimbursement;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CalculateGroupReimbursementService {

    private final GroupService groupService;
    private final ExpenseService expenseService;
    private final ExchangeRateConverter exchangeRateConverter;
    private final BalanceCalculator balanceCalculator;
    private final ReimbursementCalculator reimbursementCalculator;

    public List<Reimbursement> calculateReimbursements(UUID groupId) {
        Group group = groupService.loadGroupById(groupId).orElseThrow();
        List<User> groupMembers = groupService.loadGroupMembers(groupId);
        List<GroupExpense> groupExpenses = expenseService.loadGroupExpenses(groupId);

        List<GroupExpense> convertedExpenses = groupExpenses.stream()
                .map(exchangeRateConverter::convert)
                .toList();
        List<Balance> groupBalances = balanceCalculator.calculateGroupBalances(convertedExpenses, group, groupMembers);
        return reimbursementCalculator.calculateGroupReimbursements(groupBalances, group);
    }
}
