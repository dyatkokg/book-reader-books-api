package me.dyatkokg.bookreaderbooksapi.controller;

import lombok.RequiredArgsConstructor;
import me.dyatkokg.bookreaderbooksapi.dto.AllBookDTO;
import me.dyatkokg.bookreaderbooksapi.dto.BookTagDTO;
import me.dyatkokg.bookreaderbooksapi.service.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService service;

    @PatchMapping("add")
    public BookTagDTO addTags(@RequestBody BookTagDTO book) {
        return service.addTags(book);
    }

    @PatchMapping("remove")
    public BookTagDTO deleteTags(@RequestBody BookTagDTO book) {
        return service.removeTags(book);
    }

    @GetMapping
    public List<String> allTags(){
        return service.allTags();
    }

    @GetMapping("books")
    public List<AllBookDTO> findByTag(@RequestParam("tag") String tag){
        return service.findByTag(tag);
    }
}
