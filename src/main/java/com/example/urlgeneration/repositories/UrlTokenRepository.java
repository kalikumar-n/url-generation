package com.example.urlgeneration.repositories;

import com.example.urlgeneration.model.UrlToken;
import com.example.urlgeneration.projections.UrlTokenProjection;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlTokenRepository extends JpaRepository<UrlToken, Long> {
    UrlToken findByToken(String token);

    @Query("SELECT ut.id AS id, ut.token AS token, ut.active AS active, ut.user.email as email, ut.user.phoneNo as phoneNo " +
            "FROM UrlToken ut WHERE ut.active = :status")
    List<UrlTokenProjection> findByStatus(Boolean status);

    @Modifying
    @Query("UPDATE UrlToken ut SET ut.active = :status WHERE ut.id = :id")
    void updateActive(@Param("id") Long id, @Param("status") Boolean status);
}