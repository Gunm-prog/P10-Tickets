package com.emilie.Lib7.Repositories;


import com.emilie.Lib7.Models.Entities.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {

    List<Library> findAll();

    Optional<Library> findById(Long id);


    Optional<Library> findByName(String name);
}

