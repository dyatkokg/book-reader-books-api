package me.dyatkokg.bookreaderbooksapi.mapper;

import me.dyatkokg.bookreaderbooksapi.dto.AllBookDTO;
import me.dyatkokg.bookreaderbooksapi.dto.BookDTO;
import me.dyatkokg.bookreaderbooksapi.dto.ReadBookDTO;
import me.dyatkokg.bookreaderbooksapi.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(source = "owner.id", target = "owner")
    BookDTO toDTO(Book book);

    @Mapping(target = "owner", ignore = true)
    Book toEntity(BookDTO dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "author", target = "author")
    AllBookDTO toEntityView(Book book);

    @Mapping(source = "text", target = "text")
    ReadBookDTO toEntityReading(Book book);


}
