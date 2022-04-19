package me.dyatkokg.bookreaderbooksapi.controller;

import lombok.RequiredArgsConstructor;
import me.dyatkokg.bookreaderbooksapi.entity.Book;
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
    public ResponseEntity<Book> uploadBook(@RequestParam("name") String name,@RequestParam("author") String author,
            @RequestParam("file") MultipartFile file){
        return bookService.parse(name,author,file);
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> findById(@PathVariable("id") String id) {
        return bookService.getById(id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Book> delete(@PathVariable("id") String id) {
        return bookService.remove(id);
    }

    @GetMapping("all")
    public ResponseEntity<List<Book>> findAll() {
        return bookService.findAll();
    }
}
