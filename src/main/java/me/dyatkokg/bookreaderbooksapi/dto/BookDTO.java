package me.dyatkokg.bookreaderbooksapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.dyatkokg.bookreaderbooksapi.entity.BookPage;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private String id;

    private String name;

    private String author;

    private List<BookPage> page;

    private String owner;
}
