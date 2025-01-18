package com.github.erf88.realmeet.domain.repository;

import com.github.erf88.realmeet.domain.entity.Room;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import aj.org.objectweb.asm.commons.Remapper;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByIdAndActive(Long id, Boolean active);

}
