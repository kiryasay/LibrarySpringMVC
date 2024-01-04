package kiryasay.spring.dao;

import kiryasay.spring.models.Book;
import kiryasay.spring.models.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDao {
    private final JdbcTemplate jdbcTemplate;
    public BookDao(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate;}
    public List<Book> allBook(){ return jdbcTemplate.query("SELECT * FROM Book", new BookMapper());}

    public  List<Book> personBooks(int id) { return  jdbcTemplate.query("SELECT * FROM Book WHERE person_id = ?", new Object[]{id}, new BookMapper());}

    public Book show(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id = ?", new Object[]{id}, new BookMapper()).stream().findAny().orElse(null);
    }
    public void delete(int id) { jdbcTemplate.update("DELETE FROM Book WHERE book_id = ?", id);}
    public void update(int id, Book book) {jdbcTemplate.update("UPDATE Book SET book_title = ?, book_author = ?, book_age = ? WHERE book_id = ?",
            book.getTitle(), book.getAuthor(), book.getAge(), id );}
    public void addPerson(int id, Person person) { jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE book_id = ?", person.getId(), id);}
    public void save(Book book)
    {
        jdbcTemplate.update("INSERT INTO Book(book_title, book_author, book_age) VALUES(?,?,?)",
                book.getTitle(), book.getAuthor(), book.getAge());
    }
    public void release(int id) {
        jdbcTemplate.update("UPDATE Book SET person_id = null WHERE book_id = ?", id);
    }
    public void addOwner(int book_id, int person_id)
    {
        jdbcTemplate.update("UPDATE Book set person_id = ? WHERE book_id = ?", person_id, book_id);
    }

}
