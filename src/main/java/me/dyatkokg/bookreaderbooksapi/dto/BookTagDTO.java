package me.dyatkokg.bookreaderbooksapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookTagDTO {

    private String id;

    private List<String> tags;
}
