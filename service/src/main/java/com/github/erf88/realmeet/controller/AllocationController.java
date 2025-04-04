package com.github.erf88.realmeet.controller;

import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.supplyAsync;

import com.github.erf88.realmeet.api.facade.AllocationsApi;
import com.github.erf88.realmeet.api.model.AllocationDTO;
import com.github.erf88.realmeet.api.model.CreateAllocationDTO;
import com.github.erf88.realmeet.api.model.UpdateAllocationDTO;
import com.github.erf88.realmeet.service.AllocationService;
import com.github.erf88.realmeet.util.ResponseEntityUtils;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AllocationController implements AllocationsApi {
    private final Executor controllersExecutor;
    private final AllocationService allocationService;

    @Override
    public CompletableFuture<ResponseEntity<AllocationDTO>> createAllocation(
        String apiKey,
        CreateAllocationDTO createAllocationDTO
    ) {
        return supplyAsync(() -> allocationService.createAllocation(createAllocationDTO), controllersExecutor)
            .thenApply(ResponseEntityUtils::created);
    }

    @Override
    public CompletableFuture<ResponseEntity<Void>> deleteAllocation(String apiKey, Long id) {
        return runAsync(() -> allocationService.deleteAllocation(id), controllersExecutor)
            .thenApply(ResponseEntityUtils::noContent);
    }

    @Override
    public CompletableFuture<ResponseEntity<Void>> updateAllocation(
        String apiKey,
        Long id,
        UpdateAllocationDTO updateAllocationDTO
    ) {
        return runAsync(() -> allocationService.updateAllocation(id, updateAllocationDTO), controllersExecutor)
            .thenApply(ResponseEntityUtils::noContent);
    }

    @Override
    public CompletableFuture<ResponseEntity<List<AllocationDTO>>> listAllocations(
        String apiKey,
        String employeeEmail,
        Long roomId,
        LocalDate startAt,
        LocalDate endAt,
        String orderBy,
        Integer limit,
        Integer page
    ) {
        return supplyAsync(
                () -> allocationService.listAllocations(employeeEmail, roomId, startAt, endAt, orderBy, limit, page),
                controllersExecutor
            )
            .thenApply(ResponseEntityUtils::ok);
    }
}
