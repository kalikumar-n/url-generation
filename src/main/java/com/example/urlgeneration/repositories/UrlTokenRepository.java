package com.example.urlgeneration.repositories;

import com.example.urlgeneration.model.UrlToken;
import com.example.urlgeneration.projections.UrlTokenProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlTokenRepository extends JpaRepository<UrlToken, Long> {
    UrlToken findByToken(String token);

    @Query("SELECT ut.token AS token, ut.active AS active, ut.user.email as email, ut.user.phoneNo as phoneNo " +
            "FROM UrlToken ut WHERE ut.active = :status")
    List<UrlTokenProjection> findByStatus(Boolean status);

}