package kiryasay.spring.services;

import kiryasay.spring.models.Book;
import kiryasay.spring.models.Person;
import kiryasay.spring.repositories.BookRepository;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final EntityManager entityManager;
    @Autowired
    public BookService(BookRepository bookRepository, EntityManager entityManager) {
        this.bookRepository = bookRepository;
        this.entityManager = entityManager;
    }

    public List<Book> findAll()
    {
        return bookRepository.findAll();
    }

    public List<Book> findAll(int page, int booksPerPage) {
        return  bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }
    public List<Book> findAll(boolean sort)
    {
        return bookRepository.findAll(Sort.by("dateOfCreate"));
    }
    public Book findById(int id)
    {
        Book book = bookRepository.findById(id).orElse(null);
        return book;
    }
    public List<Book> findByOwner(Person person)
    {
       List<Book> books = bookRepository.findByOwner(person);
       for(Book book: books){
           Date currentDay = new Date();
           System.out.println(currentDay.getTime());
           if(currentDay.getTime() - book.getTakeAt().getTime() > 100000)
               book.setOverdue(true);
       }
       return books;
    }

    public Book findByTitle(String title) {
        return bookRepository.findBookByTitle(title);
    }

    @Transactional
    public void save(Book book) { bookRepository.save(book);}

    @Transactional
    public void update(int id, Book book)
    {
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id){bookRepository.deleteById(id);}

    @Transactional
    public void setOwner(int id, Person person)
    {
        Book book = bookRepository.findById(id).orElse(null);
        if(book != null){
            person.addBook(book);
            book.setTakeAt(new Date());
            bookRepository.save(book);
        }
    }
    @Transactional
    public void release(int id){
        Book book = bookRepository.findById(id).orElse(null);
        if(book != null){
            book.setOwner(null);
            book.setTakeAt(null);
            bookRepository.save(book);
        }

    }


}
