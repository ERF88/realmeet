package com.github.erf88.realmeet.controller;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import com.github.erf88.realmeet.api.facade.RoomsApi;
import com.github.erf88.realmeet.api.model.RoomDTO;
import com.github.erf88.realmeet.service.RoomService;
import com.github.erf88.realmeet.util.ResponseEntityUtils;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RoomController implements RoomsApi {

    private final Executor controllersExecutor;
    private final RoomService roomService;

    @Override
    public CompletableFuture<ResponseEntity<RoomDTO>> getRoom(Long id) {
        return supplyAsync(()-> roomService.getRoomById(id), controllersExecutor).thenApply(ResponseEntityUtils::ok);
    }

}
