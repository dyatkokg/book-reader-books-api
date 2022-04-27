package me.dyatkokg.bookreaderbooksapi.controller;

import lombok.RequiredArgsConstructor;
import me.dyatkokg.bookreaderbooksapi.dto.AllBookDTO;
import me.dyatkokg.bookreaderbooksapi.dto.BookDTO;
import me.dyatkokg.bookreaderbooksapi.entity.BookPage;
import me.dyatkokg.bookreaderbooksapi.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("upload")
    public ResponseEntity<BookDTO> uploadBook(@RequestParam("name") String name, @RequestParam("author") String author,
                                              @RequestParam("file") MultipartFile file) {
        return bookService.parse(name, author, file);
    }

    @GetMapping("{id}")
    public ResponseEntity<BookPage> findById(@PathVariable("id") String id, @RequestParam("page") Integer page) {
        return bookService.getPageByBookId(id, page);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<BookDTO> delete(@PathVariable("id") String id) {
        return bookService.remove(id);
    }

    @GetMapping("all")
    public ResponseEntity<List<AllBookDTO>> findAll() {
        return bookService.findAll();
    }
}
