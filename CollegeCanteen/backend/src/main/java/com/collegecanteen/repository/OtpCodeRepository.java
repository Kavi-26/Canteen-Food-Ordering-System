package com.collegecanteen.repository;

import com.collegecanteen.model.OtpCode;
import com.collegecanteen.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OtpCodeRepository extends JpaRepository<OtpCode, Long> {
    Optional<OtpCode> findByOtpCodeAndUserAndIsUsedFalse(String otpCode, User user);
}
