package com.emilie.Lib10.Repositories;
import com.emilie.Lib10.Models.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByLastName(String lastName);

    @Query(value="SELECT DISTINCT user FROM Loan loan " +
            "WHERE DATEDIFF(DATE(NOW()), loan.loanEndDate) >= 0 " +
            "AND loan.returned = false ")
    List<User> findUsersWithDelayedLoans();

}
