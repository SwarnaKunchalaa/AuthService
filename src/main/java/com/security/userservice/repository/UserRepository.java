package com.security.userservice.repository;

import com.security.userservice.model.Student;
import com.security.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Override
    Optional<User> findById(Integer id);

    Optional<User> findByUsername(String username);

}
