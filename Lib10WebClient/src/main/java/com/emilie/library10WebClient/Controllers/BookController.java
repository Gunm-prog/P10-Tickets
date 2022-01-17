package com.emilie.library10WebClient.Controllers;


import com.emilie.library10WebClient.Entities.Book;
import com.emilie.library10WebClient.Entities.Copy;
import com.emilie.library10WebClient.Entities.Library;
import com.emilie.library10WebClient.Proxy.FeignProxy;
import com.emilie.library10WebClient.Security.JwtProperties;
import com.emilie.library10WebClient.Security.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/books")
public class BookController {


    private static final String BOOK_SEARCH="bookSearch";
    private static final String BOOK_SEARCH_RESULT="bookSearchResult";
    private static final String BOOK_DETAILS_VIEW="bookDetails";
    private static final String COPY_ATT="copy";

    private final FeignProxy feignProxy;


    @Autowired
    public BookController(FeignProxy feignProxy) {
        this.feignProxy=feignProxy;

    }


    @GetMapping("/searchCopies")
    public String BookSearch(@CookieValue(value=JwtProperties.HEADER, required=false) String accessToken, Model model) {
        if (accessToken != null) {
            int userId=JwtTokenUtils.getUserIdFromJWT( accessToken );
            model.addAttribute( "currentUserId", userId );
            model.addAttribute( "userFirstname", JwtTokenUtils.getFirstnameFromJWT( accessToken ) );
            model.addAttribute( "userLastname", JwtTokenUtils.getLastnameFromJWT( accessToken ) );
        }
        model.addAttribute( "copy", new Copy() );
        List<Library> libraries=feignProxy.getLibraryList();
        model.addAttribute( "libraries", libraries );
        return BOOK_SEARCH;
    }


    @GetMapping("/searchResult")
    public String BookSearchResult(@CookieValue(value=JwtProperties.HEADER, required=false) String accessToken,
                                   @ModelAttribute(COPY_ATT) Copy copyInfo, Model model) {
        if (accessToken != null) {
            int userId=JwtTokenUtils.getUserIdFromJWT( accessToken );
            model.addAttribute( "currentUserId", userId );
            model.addAttribute( "userFirstname", JwtTokenUtils.getFirstnameFromJWT( accessToken ) );
            model.addAttribute( "userLastname", JwtTokenUtils.getLastnameFromJWT( accessToken ) );
        }
        Long libraryId=copyInfo.getLibraryDto().getLibraryId();
        String title=!copyInfo.getBookDto().getTitle().equals( "" ) ? copyInfo.getBookDto().getTitle() : null; //comme un bloc if else
        String isbn=!copyInfo.getBookDto().getIsbn().equals( "" ) ? copyInfo.getBookDto().getIsbn() : null;
        String firstName=!copyInfo.getBookDto().getAuthorDto().getFirstName().equals( "" ) ? copyInfo.getBookDto().getAuthorDto().getFirstName() : null;
        String lastName=!copyInfo.getBookDto().getAuthorDto().getLastName().equals( "" ) ? copyInfo.getBookDto().getAuthorDto().getLastName() : null;
        List<Book> books=feignProxy.searchBooks( libraryId, title, isbn, firstName, lastName );


        for (Book book : books) {
            for (Copy copy : book.getCopyDtos()) {
                boolean isAlreadyListed=false;
                int libKey=0;
                for (int i=0; i < book.getLibraries().size(); i++) {
                    if (book.getLibraries().get( i ).getLibraryId().equals( copy.getLibraryDto().getLibraryId() )) {
                        isAlreadyListed=true;
                        libKey=i;
                    }
                }
                if (!isAlreadyListed) {
                    Library library1=new Library();
                    library1.setLibraryId( copy.getLibraryDto().getLibraryId() );
                    library1.setName( copy.getLibraryDto().getName() );
                    book.getLibraries().add( library1 );
                    libKey=book.getLibraries().lastIndexOf( library1 );
                }

                Library lib=book.getLibraries().get( libKey );
                if (copy.isAvailable()) {
                    lib.setNbAvailableCopies( lib.getNbAvailableCopies() + 1 );
                }
                lib.setNbCopies( lib.getNbCopies() + 1 );
            }
        }

        model.addAttribute( "books", books );

        return BOOK_SEARCH_RESULT;
    }


    @GetMapping("/details/{id}")
    public String getById(@CookieValue(value=JwtProperties.HEADER, required=false) String accessToken, @PathVariable("id") Long id, Model model) {
        if (accessToken != null) {
            int userId=JwtTokenUtils.getUserIdFromJWT( accessToken );
            model.addAttribute( "currentUserId", userId );
            model.addAttribute( "userFirstname", JwtTokenUtils.getFirstnameFromJWT( accessToken ) );
            model.addAttribute( "userLastname", JwtTokenUtils.getLastnameFromJWT( accessToken ) );
        }
        Book book=feignProxy.getBookById( id );

        model.addAttribute( "book", book );

        return BOOK_DETAILS_VIEW;
    }


}
