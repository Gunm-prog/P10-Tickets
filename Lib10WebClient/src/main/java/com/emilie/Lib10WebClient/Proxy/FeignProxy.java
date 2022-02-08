package com.emilie.Lib10WebClient.Proxy;


import com.emilie.Lib10WebClient.Entities.*;
import com.emilie.Lib10WebClient.Security.JwtProperties;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name="bookFeignClient", url="localhost:8081")
public interface FeignProxy {


    /* ===Book ===*/
    @GetMapping("/api/v1/books/{id}")
    Book getBookById(@PathVariable Long id);


    @GetMapping("/api/v1/books/search")
    List<Book> searchBooks(@RequestParam(value="libraryId", required=false) Long libraryId,
                           @RequestParam(value="title", required=false) String title,
                           @RequestParam(value="isbn", required=false) String isbn,
                           @RequestParam(value="firstname", required=false) String firstName,
                           @RequestParam(value="lastname", required=false) String lastName);


    /* ===Library ===*/
    @GetMapping("/api/v1/libraries/libraryList")
    List<Library> getLibraryList();

    @GetMapping("/api/v1/libraries/{id}")
    Library getLibraryById(@PathVariable Long id);


    /* ===User ===*/

    @PostMapping("/register/customer")
    String newCustomerAccount(@RequestBody User newUser);

    @PutMapping("/api/v1/users/update")
    String updateUser(@RequestHeader(JwtProperties.HEADER) String accessToken, @RequestBody User user);

    @PostMapping("/authenticate")
    List<String> login(@RequestBody UserAccountLogin accountDto);

    @GetMapping("/api/v1/users/userAccount")
    User getLoggedUser(@RequestHeader(JwtProperties.HEADER) String accessToken);

    @PutMapping("/api/v1/loans/extendLoan/{id}")
    ResponseEntity<?> extendLoan(@PathVariable(value="id") Long id,
                                 @RequestHeader(JwtProperties.HEADER) String accessToken);

    /* === Reservation ===*/

    @DeleteMapping("api/v1/reservations/cancel/{id}")
    ResponseEntity<?> cancelReservation(@RequestHeader(JwtProperties.HEADER) String accessToken,
                                        @PathVariable(value="id") Long id);

    @PostMapping("api/v1/reservations/new")
    ResponseEntity<?> createReservation(@RequestHeader(JwtProperties.HEADER) String accessToken,
                                        @RequestBody Reservation reservation);
}


