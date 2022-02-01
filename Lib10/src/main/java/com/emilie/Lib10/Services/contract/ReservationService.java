package com.emilie.Lib10.Services.contract;

import com.emilie.Lib10.Exceptions.*;
import com.emilie.Lib10.Models.Dtos.BookDto;
import com.emilie.Lib10.Models.Dtos.ReservationDto;
import com.emilie.Lib10.Models.Dtos.UserDto;
import javassist.NotFoundException;

import java.util.Date;
import java.util.List;

public interface ReservationService {

    ReservationDto findById (Long id) throws ReservationNotFoundException;

    /**
     * @return ReservationDto
     * @throws UserNotFoundException, BookNotFoundException, ReservationAlreadyExistException, UnauthorizedException
     */
    ReservationDto create (ReservationDto reservationDto )
            throws UserNotFoundException, BookNotFoundException, ReservationAlreadyExistException, UnauthorizedException;

    ReservationDto getNextReservationForBook(BookDto bookDto) throws NotFoundException;
    ReservationDto activeReservation(ReservationDto reservationDto) throws NotFoundException;
    ReservationDto haveActiveReservationForUser(UserDto userDto, BookDto bookDto) throws NotFoundException;

    List<ReservationDto> findAll();
    List<ReservationDto> findReservationByUserId(Long userId);
    List<ReservationDto> getReservationsByBookId(Long bookId);


    void deleteById (Long id) throws ReservationNotFoundException;

    void haveAccess(UserDto loggedUser, ReservationDto reservationDto) throws UnauthorizedException;

    Date getMinExpectedReturnDate(BookDto bookDto);
    int getNmbReservationForBook(BookDto bookDto);
}
