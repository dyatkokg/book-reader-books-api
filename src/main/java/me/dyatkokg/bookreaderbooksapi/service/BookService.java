package me.dyatkokg.bookreaderbooksapi.service;

import me.dyatkokg.bookreaderbooksapi.dto.AllBookDTO;
import me.dyatkokg.bookreaderbooksapi.dto.BookDTO;
import me.dyatkokg.bookreaderbooksapi.entity.BookPage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {

    ResponseEntity<BookPage> getPageByBookId(String id, Integer page,String header);

    ResponseEntity<BookDTO> remove(String id,String header);

    List<AllBookDTO> findAllByOwner(String header);

    ResponseEntity<BookDTO> parse(String name, String author, MultipartFile file,String header);
}
