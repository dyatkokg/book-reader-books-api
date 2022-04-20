package me.dyatkokg.bookreaderbooksapi.service;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.dyatkokg.bookreaderbooksapi.dto.AllBookDTO;
import me.dyatkokg.bookreaderbooksapi.dto.BookDTO;
import me.dyatkokg.bookreaderbooksapi.dto.ReadBookDTO;
import me.dyatkokg.bookreaderbooksapi.entity.Book;
import me.dyatkokg.bookreaderbooksapi.entity.BookPage;
import me.dyatkokg.bookreaderbooksapi.exception.BookNotFoundException;
import me.dyatkokg.bookreaderbooksapi.exception.IncorrectFileTypeException;
import me.dyatkokg.bookreaderbooksapi.mapper.BookMapper;
import me.dyatkokg.bookreaderbooksapi.repository.BookRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;
    private final BookMapper mapper;

    public ResponseEntity<BookPage> getPageByBookId(String id,Integer page) {
        ReadBookDTO readBook = repository.findById(id).map(mapper::toEntityReading).orElseThrow(BookNotFoundException::new);
        Optional<BookPage> bookPage = readBook.getPage().stream().filter(e -> e.getNum().equals(page)).findAny();
        return bookPage.map(ResponseEntity::ok).orElseThrow(()->new  BookNotFoundException(id));
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
        if (Objects.requireNonNull(file.getContentType()).equals(MediaType.TEXT_PLAIN_VALUE)) {
            InputStream inputStream = file.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            Book book = new Book();
            List<String> collect = bufferedReader.lines().map(e -> new String(e.getBytes(), StandardCharsets.UTF_8)).collect(Collectors.toList());
            AtomicInteger index = new AtomicInteger(1);
            List<BookPage> bookPages = Lists.partition(collect, 100).stream().map(e -> new BookPage(index.getAndIncrement(), e)).collect(Collectors.toList());
            bookPages.forEach(e -> e.setTotalPages(bookPages.size()));
            book.setPage(bookPages);
            book.setName(name);
            book.setAuthor(author);
            return ResponseEntity.ok(mapper.toDTO(repository.save(book)));
        } else throw new IncorrectFileTypeException();
    }

}
