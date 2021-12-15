package com.emilie.Lib7.Repositories;

import com.emilie.Lib7.Models.Entities.Copy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CopyRepository extends JpaRepository<Copy, Long> {

    @Query(value="SELECT copy FROM Copy copy " +
            "JOIN copy.book book " +
            "JOIN book.author author " +
            "JOIN copy.library library " +
            "WHERE (" +
            "(" +
            " LOWER(author.firstName) LIKE '%' || LOWER(:firstname) || '%' " +
            "OR  LOWER(author.lastName) LIKE '%' || LOWER(:lastname) || '%' " +
            "OR  LOWER(book.title) LIKE '%' || LOWER(:title) || '%' " +
            "OR  LOWER(book.isbn) LIKE '%' || LOWER(:isbn) || '%' " +
            " ) " +
            ")")
    List<Copy> searchCopies(@Param("firstname") String firstname, @Param("lastname") String lastname, @Param("title") String title, @Param("isbn") String isbn);

    @Query(value="SELECT copy FROM Copy copy " +
            "JOIN copy.book book " +
            "JOIN book.author author " +
            "JOIN copy.library library " +
            "WHERE (" +
            "(" +
            " LOWER(author.firstName) LIKE '%' || LOWER(:firstname) || '%' " +
            "OR  LOWER(author.lastName) LIKE '%' || LOWER(:lastname) || '%' " +
            "OR  LOWER(book.title) LIKE '%' || LOWER(:title) || '%' " +
            "OR  LOWER(book.isbn) LIKE '%' || LOWER(:isbn) || '%' " +
            " ) AND library.libraryId = :libraryId " +
            ")")
    List<Copy> searchCopiesByLibrary(@Param("libraryId") Long libraryId, @Param("firstname") String firstname, @Param("lastname") String lastname, @Param("title") String title, @Param("isbn") String isbn);


    Copy save(Copy copy);


    Optional<Copy> findById(Long id);

}
