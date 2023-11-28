package pl.kondziet.springbackend.application.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.kondziet.springbackend.application.domain.model.id.UserId;

@Data
@AllArgsConstructor
@Builder
public class Reimbursement {

    private UserId debtorId;
    private UserId creditorId;
    private Money money;
}
