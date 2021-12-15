package com.emilie.Lib7.Models.Dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
public class LibraryDto implements Serializable {

    private static final long serialVersionUID=1L;

    private Long libraryId;
    private String name;
    private String phoneNumber;
    private AddressDto addressDto;
    private Set<CopyDto> copyDtos;


}
