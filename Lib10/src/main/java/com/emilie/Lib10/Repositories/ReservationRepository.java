package com.emilie.Lib10.Repositories;

import com.emilie.Lib10.Models.Entities.Reservation;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Override
    /*Optional<Reservation> findById(Long id);*/

    Reservation save (Reservation reservation);

    void deleteById (Long id);

    @Query(value="SELECT r FROM Reservation r " +
            "WHERE r.book.id = :bookId")
    List<Reservation> findByBookId(@Param("bookId") Long bookId);

    @Query(value="SELECT r FROM Reservation r " +
            "WHERE r.book.id = :bookId " +
            "AND r.isActive = false " +
            "AND r.reservationStartDate = (SELECT MIN(r1.reservationStartDate) " +
            "   FROM Reservation r1" +
            "   WHERE r1.book.id = :bookId)")
    Optional<Reservation> findOlderByBookId(@Param("bookId") Long bookId);

    @Query(value="SELECT r FROM Reservation r " +
            "WHERE r.user.id = :userId")
    List<Reservation> findReservationByUserId(@Param("userId") Long userId);

    @Query(value="SELECT r FROM Reservation r " +
            "WHERE r.book.id = :bookId " +
            "AND r.user.id = :userId " +
            "AND r.isActive = true")
    Optional<Reservation> findActiveReservationForUserByBookId(@Param("userId") Long userId, @Param("bookId") Long bookId);

    @Query(value="SELECT (COUNT(r)+1) FROM Reservation r " +
            "WHERE r.book.id = :bookId " +
            "AND r.reservationStartDate < (SELECT r1.reservationStartDate " +
            " FROM Reservation r1" +
            "   WHERE r1.book.id = :bookId " +
            "   AND r1.user.id = :userId )")
    int getUserPosition(@Param("bookId") Long bookId, @Param("userId") Long userId);

    @Query(value="SELECT MAX(l.loanEndDate) FROM Loan l " +
            "WHERE l.copy.book.bookId = :bookId ")
    Date getMinExpectedReturnDate(@Param("bookId") Long bookId);

    @Query(value="SELECT (COUNT(c)*2) FROM Copy c " +
            "WHERE c.book.bookId = :bookId ")
    int getMaxReservationForBook(@Param("bookId") Long bookId);

  /*  @Query(value="SELECT COUNT(r) FROM Reservation r " +
            "WHERE r.book.bookId = :bookId ")*/
 //   int getNmbReservationForBook(@Param("bookId") Long bookId);
}
