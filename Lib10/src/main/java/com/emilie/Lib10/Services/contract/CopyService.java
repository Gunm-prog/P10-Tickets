package com.emilie.Lib10.Services.contract;

import com.emilie.Lib10.Exceptions.CopyAlreadyExistException;
import com.emilie.Lib10.Exceptions.CopyNotFoundException;
import com.emilie.Lib10.Models.Dtos.CopyDto;

import java.util.List;

public interface CopyService {

    CopyDto findById(Long id) throws CopyNotFoundException;

    CopyDto save(CopyDto copyDto) throws CopyAlreadyExistException;

    CopyDto update(CopyDto copyDto);

    void deleteById(Long id) throws CopyNotFoundException;

    List<CopyDto> findAll();

    List<CopyDto> searchCopies(Long libraryId, String title, String isbn, String firstName, String lastName);


}
