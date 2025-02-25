package com.github.erf88.realmeet.utils;

import static com.github.erf88.realmeet.utils.TestConstants.*;

import com.github.erf88.realmeet.api.model.CreateAllocationDTO;
import com.github.erf88.realmeet.api.model.CreateRoomDTO;
import com.github.erf88.realmeet.api.model.UpdateAllocationDTO;
import com.github.erf88.realmeet.domain.entity.Allocation;
import com.github.erf88.realmeet.domain.entity.Client;
import com.github.erf88.realmeet.domain.entity.Employee;
import com.github.erf88.realmeet.domain.entity.Room;
import java.time.OffsetDateTime;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class TestDataCreator {

    public static Room.Builder newRoomBuilder() {
        return Room.newBuilder().name(DEFAULT_ROOM_NAME).seats(DEFAULT_ROOM_SEATS);
    }

    public static CreateRoomDTO newCreateRoomDTO() {
        return new CreateRoomDTO().name(DEFAULT_ROOM_NAME).seats(DEFAULT_ROOM_SEATS);
    }

    public static Allocation.Builder newAllocationBuilder() {
        return Allocation
            .newBuilder()
            .room(newRoomBuilder().id(DEFAULT_ROOM_ID).build())
            .subject(DEFAULT_ALLOCATION_SUBJECT)
            .employee(Employee.newBuilder().name(DEFAULT_EMPLOYEE_NAME).email(DEFAULT_EMPLOYEE_EMAIL).build())
            .startAt(DEFAULT_ALLOCATION_START_AT)
            .endAt(DEFAULT_ALLOCATION_END_AT);
    }

    public static CreateAllocationDTO newCreateAllocationDTO() {
        return new CreateAllocationDTO()
            .roomId(DEFAULT_ROOM_ID)
            .subject(DEFAULT_ALLOCATION_SUBJECT)
            .employeeName(DEFAULT_EMPLOYEE_NAME)
            .employeeEmail(DEFAULT_EMPLOYEE_EMAIL)
            .startAt(DEFAULT_ALLOCATION_START_AT)
            .endAt(DEFAULT_ALLOCATION_END_AT);
    }

    public static UpdateAllocationDTO newUpdateAllocationDTO() {
        return new UpdateAllocationDTO()
            .subject(DEFAULT_ALLOCATION_SUBJECT)
            .startAt(DEFAULT_ALLOCATION_START_AT)
            .endAt(DEFAULT_ALLOCATION_END_AT);
    }

    public static Employee.Builder newEmployeeBuilder() {
        return Employee.newBuilder().name(DEFAULT_EMPLOYEE_NAME).email(DEFAULT_EMPLOYEE_EMAIL);
    }

    public static Client.Builder newClientBuilder() {
        return Client.newBuilder().apiKey(TEST_CLIENT_API_KEY).description(TEST_CLIENT_DESCRIPTION).active(true);
    }
}
