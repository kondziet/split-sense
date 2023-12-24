package pl.kondziet.springbackend.infrastructure.adapter.web;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kondziet.springbackend.domain.model.entity.Reimbursement;
import pl.kondziet.springbackend.domain.model.valueobjects.GroupId;
import pl.kondziet.springbackend.domain.model.valueobjects.UserId;
import pl.kondziet.springbackend.application.port.in.AuthenticationPrincipalUseCase;
import pl.kondziet.springbackend.application.port.in.CalculateGroupReimbursementsUseCase;

import java.util.UUID;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/reimbursement")
public class ReimbursementController {

    private final AuthenticationPrincipalUseCase authenticationPrincipalUseCase;
    private final CalculateGroupReimbursementsUseCase calculateGroupReimbursementsUseCase;

    @GetMapping("/group/{groupId}")
    ResponseEntity<?> getGroupReimbursement(@PathVariable("groupId") UUID groupId) {
        UserId authenticatedUserId = authenticationPrincipalUseCase.getAuthenticatedUserId();
        GroupId reimbursementGroupId = new GroupId(groupId);

        List<Reimbursement> reimbursements = calculateGroupReimbursementsUseCase.calculateReimbursements(reimbursementGroupId);

        return ResponseEntity.ok(reimbursements);
    }
}
