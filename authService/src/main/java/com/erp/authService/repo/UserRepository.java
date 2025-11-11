package com.erp.authService.repo;

import com.erp.authService.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByUsername(String username);

}
