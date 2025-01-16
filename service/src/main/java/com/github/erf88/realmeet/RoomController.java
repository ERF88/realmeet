package com.github.erf88.realmeet;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import com.github.erf88.realmeet.api.facade.RoomsApi;
import com.github.erf88.realmeet.api.model.RoomDTO;
import com.github.erf88.realmeet.service.RoomService;
import java.util.concurrent.CompletableFuture;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RoomController implements RoomsApi {

    private final RoomService roomService;

    @Override
    public CompletableFuture<ResponseEntity<RoomDTO>> getRoom(Long id) {
        return supplyAsync(()-> ResponseEntity.ok(roomService.getRoomById(id)));
    }

}
