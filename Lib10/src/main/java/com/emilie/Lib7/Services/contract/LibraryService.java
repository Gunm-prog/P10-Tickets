
package com.emilie.Lib7.Services.contract;


import com.emilie.Lib7.Models.Dtos.CopyDto;
import com.emilie.Lib7.Models.Dtos.LibraryDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface LibraryService {


    List<LibraryDto> findAll();

    LibraryDto findById(Long id);

    LibraryDto save(LibraryDto libraryDto);

    LibraryDto update(LibraryDto libraryDto);

    boolean deleteById(Long id);

    Set<CopyDto> findCopiesByLibraryId(Long id);

}

