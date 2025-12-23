package com.bookstorevn.repositories;

import com.bookstorevn.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, String> {
    Optional<ApplicationUser> findByEmail(String email);
    Optional<ApplicationUser> findByUserName(String userName);
}
