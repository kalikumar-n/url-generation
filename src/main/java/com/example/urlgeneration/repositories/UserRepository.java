package com.example.urlgeneration.repositories;

import com.example.urlgeneration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
