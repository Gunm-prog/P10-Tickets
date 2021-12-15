package com.emilie.Lib7.Models.Dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CopyDto implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;
    private boolean available;
    private BookDto bookDto;
    private LibraryDto libraryDto;

}
