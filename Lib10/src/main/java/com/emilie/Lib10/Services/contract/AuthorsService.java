package com.emilie.Lib10.Services.contract;


import com.emilie.Lib10.Models.Dtos.AuthorDto;

import java.util.List;

public interface AuthorsService {
    List<AuthorDto> findAll();

    AuthorDto findById(Long id);

    AuthorDto save(AuthorDto authorDto);

    AuthorDto update(AuthorDto authorDto);

    void deleteById(Long id);

}
