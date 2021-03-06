package me.dyatkokg.bookreaderbooksapi.service;

import me.dyatkokg.bookreaderbooksapi.dto.AllBookDTO;
import me.dyatkokg.bookreaderbooksapi.dto.BookDTO;
import me.dyatkokg.bookreaderbooksapi.entity.BookPage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {

    BookPage getPageByBookId(String id, Integer page);

    BookDTO remove(String id);

    List<AllBookDTO> findAllByOwner();

    BookDTO parse(String name, String author, MultipartFile file);
}
