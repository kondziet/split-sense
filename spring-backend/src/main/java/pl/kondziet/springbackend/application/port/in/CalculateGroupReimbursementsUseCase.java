package pl.kondziet.springbackend.application.port.in;

import pl.kondziet.springbackend.application.domain.model.entity.Reimbursement;
import pl.kondziet.springbackend.application.domain.model.id.GroupId;

import java.util.List;

public interface CalculateGroupReimbursementsUseCase {

    List<Reimbursement> calculateReimbursements(GroupId groupId);
}
