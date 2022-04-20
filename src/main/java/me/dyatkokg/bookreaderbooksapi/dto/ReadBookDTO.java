package me.dyatkokg.bookreaderbooksapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.dyatkokg.bookreaderbooksapi.entity.BookPage;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadBookDTO {

    private List<BookPage> page;
}
