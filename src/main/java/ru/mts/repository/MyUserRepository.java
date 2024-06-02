package ru.mts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mts.model.MyUser;

import java.util.Optional;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Integer> {
    Optional<MyUser> findByEmail(String email);
}
