package com.cesantia.elections.repositories;

import com.cesantia.elections.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByCi(String ci);
    boolean existsByCi(String ci);
}
