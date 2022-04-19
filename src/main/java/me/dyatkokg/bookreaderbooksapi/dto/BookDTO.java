package me.dyatkokg.bookreaderbooksapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private String id;

    private String name;

    private String author;

    private List<String> text;

    private String owner;
}
