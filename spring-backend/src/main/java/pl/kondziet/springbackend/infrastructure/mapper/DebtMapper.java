package pl.kondziet.springbackend.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.kondziet.springbackend.adapter.in.web.dto.DebtRequest;
import pl.kondziet.springbackend.application.domain.model.entity.Debt;
import pl.kondziet.springbackend.application.domain.model.id.UserId;

import java.util.Set;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface DebtMapper {

    Set<Debt> debtRequestsToDebts(Set<DebtRequest> debtRequests);
    @Mapping(target = "debtorId", source = "debtRequest.debtorId", qualifiedByName = "idToUserId")
    Debt debtRequestToDebt(DebtRequest debtRequest);
    @Named("idToUserId")
    default UserId idToUserId(UUID id) {
        return new UserId(id);
    }
}
