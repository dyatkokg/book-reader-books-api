package me.dyatkokg.bookreaderbooksapi.service.impl;

import lombok.RequiredArgsConstructor;
import me.dyatkokg.bookreaderbooksapi.config.filter.FilterUtils;
import me.dyatkokg.bookreaderbooksapi.dto.AllBookDTO;
import me.dyatkokg.bookreaderbooksapi.dto.BookTagDTO;
import me.dyatkokg.bookreaderbooksapi.entity.Book;
import me.dyatkokg.bookreaderbooksapi.exception.BookNotFoundException;
import me.dyatkokg.bookreaderbooksapi.mapper.BookMapper;
import me.dyatkokg.bookreaderbooksapi.repository.BookRepository;
import me.dyatkokg.bookreaderbooksapi.service.TagService;
import me.dyatkokg.bookreaderbooksapi.service.TokenProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImplementation implements TagService {

    private final BookRepository repository;
    private final TokenProvider provider;
    private final BookMapper mapper;

    @Override
    public BookTagDTO addTags(BookTagDTO bookTagDTO) {
        Optional<Book> byId = repository.findById(bookTagDTO.getId());
        byId.filter(e -> e.getTags() == null).ifPresent(e -> e.setTags(new ArrayList<>()));
        byId.ifPresent(e -> e.getTags().addAll(bookTagDTO.getTags()));
        return byId.map(repository::save).map(e -> new BookTagDTO(e.getId(), e.getTags())).orElseThrow(BookNotFoundException::new);
    }

    @Override
    public BookTagDTO removeTags(BookTagDTO bookTagDTO) {
        return repository.findById(bookTagDTO.getId()).map(bk -> {
            bk.getTags().removeIf(id -> bookTagDTO.getTags().contains(id));
            return repository.save(bk);
        }).map(Book::getTags).map(e -> new BookTagDTO(bookTagDTO.getId(), e)).orElseThrow(BookNotFoundException::new);
    }

    @Override
    public List<String> allTags() {
        return repository.findByOwner(provider.getSubject(FilterUtils.getTokenFromSecurityContext()))
                .stream().map(Book::getTags).flatMap(List::stream).distinct().collect(Collectors.toList());
    }

    @Override
    public List<AllBookDTO> findByTag(String tag) {
        return repository.findAllByTagsIn(Collections.singletonList(tag)).stream().map(mapper::toEntityView).collect(Collectors.toList());
    }
}
