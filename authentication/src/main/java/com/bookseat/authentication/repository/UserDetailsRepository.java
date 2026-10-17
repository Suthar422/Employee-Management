package com.bookseat.authentication.repository;

import com.bookseat.authentication.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<Users, String> {
     Optional<Users> findByUsername(String username);
}
