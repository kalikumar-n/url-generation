package com.example.urlgeneration.repositories;

import com.example.urlgeneration.model.UrlToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface UrlTokenRepository extends JpaRepository<UrlToken, Long> {
    UrlToken findByToken(String token);
}