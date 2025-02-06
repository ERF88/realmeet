package com.github.erf88.realmeet.domain.repository;

import com.github.erf88.realmeet.domain.entity.Allocation;
import com.github.erf88.realmeet.domain.entity.Room;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, Long> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Allocation a SET a.subject = :subject, a.startAt = :startAt, a.endAt = :endAt WHERE r.id = :id")
    void updateAllocation(
        @Param("id") Long id,
        @Param("subject") String subject,
        @Param("startAt") OffsetDateTime startAt,
        @Param("endAt") OffsetDateTime endAt
    );
}
