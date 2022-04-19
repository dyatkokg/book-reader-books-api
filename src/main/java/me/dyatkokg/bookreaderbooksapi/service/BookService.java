package me.dyatkokg.bookreaderbooksapi.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.dyatkokg.bookreaderbooksapi.entity.Book;
import me.dyatkokg.bookreaderbooksapi.exception.IncorrectFileTypeException;
import me.dyatkokg.bookreaderbooksapi.repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    public ResponseEntity<Book> getById(String id) {
        return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }

    public ResponseEntity<Book> remove(String id) {
        Book deleted = repository.findById(id).orElse(null);
        if (deleted == null) {
            return ResponseEntity.noContent().build();
        } else
            repository.delete(deleted);
        return ResponseEntity.ok(deleted);
    }

    public ResponseEntity<List<Book>> findAll() {
        return ResponseEntity.ok(new ArrayList<>(repository.findAll()));
    }

    @SneakyThrows
    public ResponseEntity<Book> parse(String name, String author, MultipartFile file) {
        if(Objects.requireNonNull(file.getContentType()).endsWith(".txt") || file.getContentType().endsWith(".docx")) {
            InputStream inputStream = file.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            Book book = new Book();
            List<String> collect = bufferedReader.lines().collect(Collectors.toList());
            book.setText(collect);
            book.setName(name);
            book.setAuthor(author);
            return ResponseEntity.ok(repository.save(book));
        }else throw new IncorrectFileTypeException();
    }

}
