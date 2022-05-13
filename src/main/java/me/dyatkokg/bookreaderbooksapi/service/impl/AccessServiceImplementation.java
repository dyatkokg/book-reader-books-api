package me.dyatkokg.bookreaderbooksapi.service.impl;

import lombok.RequiredArgsConstructor;
import me.dyatkokg.bookreaderbooksapi.dto.BookAccessDTO;
import me.dyatkokg.bookreaderbooksapi.entity.Book;
import me.dyatkokg.bookreaderbooksapi.exception.BookNotFoundException;
import me.dyatkokg.bookreaderbooksapi.repository.BookRepository;
import me.dyatkokg.bookreaderbooksapi.service.AccessService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccessServiceImplementation implements AccessService {

    private final BookRepository repository;

    @Override
    public BookAccessDTO setAccess(BookAccessDTO book) {
        Optional<Book> byId = repository.findById(book.getId());
        byId.ifPresent(e -> e.getHasAccess().addAll(book.getHasAccess()));
        return byId.map(repository::save).map(e -> new BookAccessDTO(e.getId(), e.getHasAccess())).orElseThrow(BookNotFoundException::new);
    }

    @Override
    public BookAccessDTO revokeAccess(BookAccessDTO book) {
//        List<String> currentAccess=null;
//        Optional<Book> byId = repository.findById(book.getId());
//        if(byId.isPresent()){
//            Book book1 = byId.get();
//            book1.getHasAccess().removeIf(e->book.getHasAccess().contains(e));
//            repository.save(book1);
//            currentAccess=new ArrayList<>(book1.getHasAccess());
//        }
//        return new BookAccessDTO(book.getId(),currentAccess);
        return repository.findById(book.getId()).map(bk -> {
            bk.getHasAccess().removeIf(id -> book.getHasAccess().contains(id));
            return repository.save(bk);
        }).map(Book::getHasAccess).map(e -> new BookAccessDTO(book.getId(), e)).orElseThrow(BookNotFoundException::new);
    }
}
