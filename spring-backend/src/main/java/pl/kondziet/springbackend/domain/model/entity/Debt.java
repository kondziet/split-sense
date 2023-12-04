package pl.kondziet.springbackend.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.kondziet.springbackend.domain.model.valueobjects.UserId;

@Data
@AllArgsConstructor
@Builder
public class Debt {

    private UserId debtorId;
    private Money money;
}
