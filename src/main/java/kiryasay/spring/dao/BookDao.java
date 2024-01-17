package kiryasay.spring.dao;

import kiryasay.spring.models.Book;
import kiryasay.spring.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class BookDao {
    private final SessionFactory sessionFactory;
    @Autowired
    public BookDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Transactional(readOnly = true)
    public List<Book> allBook(){
        Session session = sessionFactory.getCurrentSession();

        List<Book> books = session.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        return books;
    }
    @Transactional(readOnly = true)
    public  List<Book> personBooks(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        List<Book> books = person.getBooks();
        return books;
    }

    @Transactional(readOnly = true)
    public Book show(int id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);
            }
    @Transactional()
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        session.remove(book);
    }
    @Transactional()
    public void update(int id, Book book) {
        Session session =sessionFactory.getCurrentSession();
        Book temp = session.get(Book.class, id);
        temp.setTitle(book.getTitle());
        temp.setAge(book.getAge());
        temp.setAuthor(book.getAuthor());

    }
    @Transactional()
    public Person getOwner(int id)
    {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        return book.getOwner();
    }
    public void addPerson(int id, Person person) { }

    @Transactional()
    public void save(Book book)
    {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }
    @Transactional()
    public void release(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        Person person = book.getOwner();
        book.setOwner(null);
        person.getBooks().remove(book);
    }
    @Transactional()
    public void addOwner(int book_id, int person_id)
    {
        System.out.println("Добавляю владельца");
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, book_id);
        Person person = session.get(Person.class, person_id);
        person.addBook(book);
    }

}
