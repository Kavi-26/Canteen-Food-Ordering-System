package com.collegecanteen.repository;

import com.collegecanteen.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByRollNo(String rollNo);
    Optional<User> findByEmailAndPassword(String email, String password);
}
