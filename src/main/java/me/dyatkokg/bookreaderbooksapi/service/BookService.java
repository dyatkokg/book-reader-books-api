package me.dyatkokg.bookreaderbooksapi.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.dyatkokg.bookreaderbooksapi.dto.AllBookDTO;
import me.dyatkokg.bookreaderbooksapi.dto.BookDTO;
import me.dyatkokg.bookreaderbooksapi.dto.ReadBookDTO;
import me.dyatkokg.bookreaderbooksapi.entity.Book;
import me.dyatkokg.bookreaderbooksapi.exception.BookNotFoundException;
import me.dyatkokg.bookreaderbooksapi.mapper.BookMapper;
import me.dyatkokg.bookreaderbooksapi.repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;
    private final BookMapper mapper;

    public ResponseEntity<ReadBookDTO> getById(String id) {
        return repository.findById(id)
                .map(mapper::toEntityReading)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public ResponseEntity<BookDTO> remove(String id) {
        BookDTO deleted = repository.findById(id).map(mapper::toDTO).orElseThrow(BookNotFoundException::new);
        if (deleted == null) {
            throw new BookNotFoundException();
        } else
            repository.deleteById(id);
        return ResponseEntity.ok(deleted);
    }

    public ResponseEntity<List<AllBookDTO>> findAll() {
        return ResponseEntity.ok(repository.findAll().stream().map(mapper::toEntityView).collect(Collectors.toList()));
    }

    @SneakyThrows
    public ResponseEntity<BookDTO> parse(String name, String author, MultipartFile file) {
//        if (Objects.requireNonNull(file.getContentType()).endsWith(".txt") || file.getContentType().endsWith(".docx")) {
            InputStream inputStream = file.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            Book book = new Book();
            List<String> collect = bufferedReader.lines().collect(Collectors.toList());
            book.setText(collect);
            book.setName(name);
            book.setAuthor(author);
            return ResponseEntity.ok(mapper.toDTO(repository.save(book)));
//        } else throw new IncorrectFileTypeException();
    }

}
