package com.emilie.Lib7.Repositories;

import com.emilie.Lib7.Models.Entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    Optional<Loan> findById(Long id);

    Loan save(Loan loan);

    void deleteById(Long id);

    Optional<Loan> findByCopyId(Long id);


    @Query(value="SELECT l FROM Loan l " +
            "WHERE l.user.id = :userId")
    List<Loan> findLoansByUserId(@Param("userId") Long userId);

    @Query(value="SELECT loan FROM Loan loan WHERE DATEDIFF(DATE(NOW()), loan.loanEndDate) >= 0")
    List<Loan> searchDelay();

}
