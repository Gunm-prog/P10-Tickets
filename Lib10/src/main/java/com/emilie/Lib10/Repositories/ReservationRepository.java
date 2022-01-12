package com.emilie.Lib10.Repositories;

import com.emilie.Lib10.Models.Entities.Reservation;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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

    //todo ajouter le param isActive false (on peut avoir plusieur resa active en simultané )
    //todo a tester
    @Query(value="SELECT r FROM Reservation r " +
            "WHERE r.book.id = :bookId " +
            "AND r.isActive = false " +
            "AND r.reservationStartDate = (SELECT MIN(r1.reservationStartDate) " +
            "   from Reservation r1" +
            "   where r1.book.id = :bookId)")
    Optional<Reservation> findOlderByBookId(@Param("bookId") Long bookId);

    @Query(value="SELECT r FROM Reservation r " +
            "WHERE r.user.id = :userId")
    List<Reservation> findReservationByUserId(@Param("userId") Long userId);

    @Query(value="SELECT r FROM Reservation r " +
            "WHERE r.book.id = :bookId " +
            "AND r.user.id = :userId " +
            "AND r.isActive = true")
    Optional<Reservation> findActiveReservationForUserByBookId(@Param("userId") Long userId, @Param("bookId") Long bookId);
}
