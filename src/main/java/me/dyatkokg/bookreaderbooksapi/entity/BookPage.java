package me.dyatkokg.bookreaderbooksapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookPage {

    private Integer num;

    private List<String> lines;

    private Integer totalPages;

    public BookPage(Integer num, List<String> lines) {
        this.num = num;
        this.lines = lines;
    }

}
