package com.emilie.Lib7.Repositories;


import com.emilie.Lib7.Models.Entities.Author;
import com.emilie.Lib7.Models.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


    @Query(value="SELECT DISTINCT book FROM Book book " +
            "JOIN Copy copy ON book.id = copy.book.id " +
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
    List<Book> searchBooks(@Param("title") String title,
                           @Param("isbn") String isbn,
                           @Param("firstname") String firstName,
                           @Param("lastname") String lastName);

    @Query(value="SELECT DISTINCT book FROM Book book " +
            "JOIN Copy copy ON book.id = copy.book.id " +
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
    List<Book> searchBooksByLibrary(@Param("libraryId") Long libraryId,
                                    @Param("title") String title,
                                    @Param("isbn") String isbn,
                                    @Param("firstname") String firstName,
                                    @Param("lastname") String lastName);


    List<Book> findAll();

    Optional<Book> findByTitle(String title);

    Optional<Book> findByAuthor(Author author);

    Optional<Book> findById(Long id);


}