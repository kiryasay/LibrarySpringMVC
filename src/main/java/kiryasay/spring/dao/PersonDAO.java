package kiryasay.spring.dao;

import kiryasay.spring.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final SessionFactory sessionFactory;
    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Transactional(readOnly = true)
    public List<Person> allPeople(){
        Session session = sessionFactory.getCurrentSession();

        //hibernate code
        List<Person> people = session.createQuery("SELECT p FROM Person p", Person.class).getResultList();

        return people;
    }
    @Transactional(readOnly = true)
    public Person show(int id){

        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        person.setBooks(person.getBooks());
        return person;
    }
    @Transactional(readOnly = true)
    public Optional<Person> check(String name, int age){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Person WHERE age = :personAge and name = :personName", Person.class)
                .setParameter("personAge",age ).setParameter("personName", name).uniqueResultOptional();
           }

    @Transactional()
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        session.remove(person);
    }
    @Transactional()
    public void update(int id, Person person) {
        Session session = sessionFactory.getCurrentSession();
        Person temp = session.get(Person.class, id);
        temp.setName(person.getName());
        temp.setAge(person.getAge());

    }
    @Transactional()
    public void save(Person person)
    {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

}
