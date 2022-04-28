package me.dyatkokg.bookreaderbooksapi.repository;

import me.dyatkokg.bookreaderbooksapi.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findByOwner(String header);
}
