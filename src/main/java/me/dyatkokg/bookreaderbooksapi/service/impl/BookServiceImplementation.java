package me.dyatkokg.bookreaderbooksapi.service.impl;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.dyatkokg.bookreaderbooksapi.config.filter.FilterUtils;
import me.dyatkokg.bookreaderbooksapi.dto.AllBookDTO;
import me.dyatkokg.bookreaderbooksapi.dto.BookDTO;
import me.dyatkokg.bookreaderbooksapi.dto.ReadBookDTO;
import me.dyatkokg.bookreaderbooksapi.entity.Book;
import me.dyatkokg.bookreaderbooksapi.entity.BookPage;
import me.dyatkokg.bookreaderbooksapi.exception.BookNotFoundException;
import me.dyatkokg.bookreaderbooksapi.exception.IncorrectFileTypeException;
import me.dyatkokg.bookreaderbooksapi.exception.NotAuthorizedException;
import me.dyatkokg.bookreaderbooksapi.exception.PageNotFoundException;
import me.dyatkokg.bookreaderbooksapi.mapper.BookMapper;
import me.dyatkokg.bookreaderbooksapi.repository.BookRepository;
import me.dyatkokg.bookreaderbooksapi.service.BookService;
import me.dyatkokg.bookreaderbooksapi.service.TokenProvider;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImplementation implements BookService {

    private final BookRepository repository;
    private final BookMapper mapper;
    private final TokenProvider provider;

    public BookPage getPageByBookId(String id, Integer page) {
        ReadBookDTO readBook = repository.findById(id).map(mapper::toEntityReading).orElseThrow(BookNotFoundException::new);
        return readBook.getPage().stream().filter(e -> e.getNum().equals(page)).findAny().orElseThrow(PageNotFoundException::new);
    }

    public BookDTO remove(String id) {
        BookDTO deleted = repository.findById(id).map(mapper::toDTO).orElseThrow(BookNotFoundException::new);
            repository.deleteById(id);
            return deleted;
    }

    @Override
    public List<AllBookDTO> findAllByOwner() {
        return repository.findByOwner(provider.getSubject(FilterUtils.getTokenFromSecurityContext()))
                .stream().map(mapper::toEntityView).collect(Collectors.toList());
    }

    @SneakyThrows
    public BookDTO parse(String name, String author, MultipartFile file) {
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
            String subject = provider.getSubject(FilterUtils.getTokenFromSecurityContext());
            book.setOwner(subject);
            book.setHasAccess(Collections.singletonList(subject));
            return mapper.toDTO(repository.save(book));
        } else throw new IncorrectFileTypeException();
    }

}
