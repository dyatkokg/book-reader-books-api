package me.dyatkokg.bookreaderbooksapi.repository;

import me.dyatkokg.bookreaderbooksapi.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {
}
