package me.dyatkokg.bookreaderbooksapi.controller;

import lombok.RequiredArgsConstructor;
import me.dyatkokg.bookreaderbooksapi.dto.BookAccessDTO;
import me.dyatkokg.bookreaderbooksapi.service.AccessService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("access")
@RequiredArgsConstructor
public class AccessController {

    private final AccessService accessService;

    @PatchMapping("grant")
    public BookAccessDTO addAccess(@RequestBody BookAccessDTO book) {
        return accessService.setAccess(book);
    }

    @PatchMapping("revoke")
    public BookAccessDTO revokeAccess(@RequestBody BookAccessDTO book) {
        return accessService.revokeAccess(book);
    }

}
