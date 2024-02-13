package kiryasay.spring.repositories;

import kiryasay.spring.models.Book;
import kiryasay.spring.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findBookByTitle(String title);
    List<Book> findByOwner(Person owner);
}
