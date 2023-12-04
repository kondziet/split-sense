package pl.kondziet.springbackend.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.kondziet.springbackend.domain.dto.DebtRequest;
import pl.kondziet.springbackend.domain.model.entity.Debt;
import pl.kondziet.springbackend.domain.model.entity.Money;
import pl.kondziet.springbackend.domain.model.valueobjects.UserId;

import java.util.Set;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface DebtMapper {

    Set<Debt> debtRequestsToDebts(Set<DebtRequest> debtRequests);
    @Mapping(target = "debtorId", source = "debtRequest.debtorId", qualifiedByName = "idToUserId")
    @Mapping(target = "money", source = "debtRequest", qualifiedByName = "debtRequestToMoney")
    Debt debtRequestToDebt(DebtRequest debtRequest);

    @Named("idToUserId")
    default UserId idToUserId(UUID id) {
        return new UserId(id);
    }

    @Named("debtRequestToMoney")
    default Money debtRequestToMoney(DebtRequest debtRequest) {
        return new Money(debtRequest.currency(), debtRequest.amount());
    }
}
