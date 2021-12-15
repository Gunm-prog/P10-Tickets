package com.emilie.library7WebClient.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class Book {

    private Long bookId;
    private String title;
    private String isbn;
    private String summary;
    private Author authorDto;
    private Set<Copy> copies= new HashSet<>();
    private List<Library> libraries = new ArrayList<>();
    private List<Copy> copyDtos = new ArrayList<>();


    @Override
    public String toString() {
        return "Book{" +
                "id=" + bookId +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", summary='" + summary + '\'' +
                ", author=" + authorDto +
                ", copies=" + copies +
                '}';
    }
}
