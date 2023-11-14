package pl.kondziet.springbackend.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.kondziet.springbackend.adapter.in.web.dto.DebtRequest;
import pl.kondziet.springbackend.adapter.out.persistence.entity.DebtJpaEntity;
import pl.kondziet.springbackend.application.domain.model.entity.Debt;
import pl.kondziet.springbackend.application.domain.model.id.UserId;
import pl.kondziet.springbackend.application.port.in.command.DebtDetail;

import java.util.Set;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface DebtMapper {

    DebtJpaEntity debtToDebtJpaEntity(Debt debt);
    Set<Debt> debtDetailsToDebts(Set<DebtDetail> debtDetails);
    Debt debtDetailToDebt(DebtDetail debtDetail);
    Set<DebtDetail> debtRequestsToDebtDetails(Set<DebtRequest> debtRequests);
    @Mapping(target = "debtorId", source = "debtorId", qualifiedByName = "idToUserId")
    DebtDetail debtRequestToDebtDetail(DebtRequest debtRequest);
    @Named("idToUserId")
    default UserId idToUserId(UUID id) {
        return new UserId(id);
    }
}
