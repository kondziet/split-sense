package pl.kondziet.springbackend.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kondziet.springbackend.model.dto.ExpenseDebtorResponse;
import pl.kondziet.springbackend.model.dto.PersonalExpenseResponse;
import pl.kondziet.springbackend.model.entity.ExpenseDebtor;
import pl.kondziet.springbackend.model.entity.PersonalExpense;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    @Mapping(target = "debtorId", source = "debtor.id")
    ExpenseDebtorResponse expenseDebtorToDto(ExpenseDebtor expenseDebtor);

    List<ExpenseDebtorResponse> expenseDebtorsToDtos(Set<ExpenseDebtor> expenseDebtors);

    @Mapping(target = "payerId", source = "payer.id")
    @Mapping(target = "debts", source = "expenseDebtors")
    PersonalExpenseResponse personalExpenseToDto(PersonalExpense personalExpense);

}
