package com.emilie.Lib7.Repositories;

import com.emilie.Lib7.Models.Entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, Long> {


    @Query(value="SELECT a FROM Author a WHERE (a.firstName LIKE :first_name and a.lastName LIKE :last_name)")
    Optional<Author> findByName(@Param("first_name") String firstName,
                                @Param("last_name") String lastName);

    Optional<Author> findById(Long id);

    Optional<Author> findByFirstName(String firstName);

    Optional<Author> findByLastName(String lastName);

    List<Author> findAll();
}
