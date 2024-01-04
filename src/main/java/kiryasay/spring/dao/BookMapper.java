package kiryasay.spring.dao;

import kiryasay.spring.models.Book;
import kiryasay.spring.models.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        Book book = new Book();

        book.setId(resultSet.getInt("book_id"));
        book.setTitle(resultSet.getString("book_title"));
        book.setAuthor(resultSet.getString("book_author"));
        book.setAge(resultSet.getInt("book_age"));
        book.setOwnerId(resultSet.getInt("person_id"));

        return book;
    }
}