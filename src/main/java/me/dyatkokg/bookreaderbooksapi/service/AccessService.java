package me.dyatkokg.bookreaderbooksapi.service;

import me.dyatkokg.bookreaderbooksapi.dto.BookAccessDTO;

public interface AccessService {

    BookAccessDTO setAccess(BookAccessDTO book);

    BookAccessDTO revokeAccess(BookAccessDTO book);
}
