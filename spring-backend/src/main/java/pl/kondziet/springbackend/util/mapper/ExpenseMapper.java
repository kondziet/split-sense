package pl.kondziet.springbackend.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kondziet.springbackend.model.dto.DebtResponse;
import pl.kondziet.springbackend.model.dto.GroupExpenseResponse;
import pl.kondziet.springbackend.model.dto.PersonalExpenseResponse;
import pl.kondziet.springbackend.model.entity.Debt;
import pl.kondziet.springbackend.model.entity.GroupExpense;
import pl.kondziet.springbackend.model.entity.PersonalExpense;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    @Mapping(target = "debtorId", source = "debtor.id")
    DebtResponse debtToDto(Debt debt);

    List<DebtResponse> debtsToDtos(Set<Debt> debts);

    @Mapping(target = "payerId", source = "payer.id")
    PersonalExpenseResponse personalExpenseToDto(PersonalExpense personalExpense);

    @Mapping(target = "payerId", source = "payer.id")
    GroupExpenseResponse groupExpenseToDto(GroupExpense groupExpense);

}
