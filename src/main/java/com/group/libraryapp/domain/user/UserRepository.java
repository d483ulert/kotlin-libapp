package com.group.libraryapp.domain.user;

import com.group.libraryapp.domain.user.loan_history.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByName(String name);

}
