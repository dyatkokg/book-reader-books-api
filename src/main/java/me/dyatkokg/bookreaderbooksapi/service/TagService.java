package me.dyatkokg.bookreaderbooksapi.service;

import me.dyatkokg.bookreaderbooksapi.dto.AllBookDTO;
import me.dyatkokg.bookreaderbooksapi.dto.BookTagDTO;

import java.util.List;

public interface TagService {

    BookTagDTO addTags(BookTagDTO bookTagDTO);

    BookTagDTO removeTags(BookTagDTO bookTagDTO);

    List<String> allTags();

    List<AllBookDTO> findByTag(String tag);
}
