package kiryasay.spring.services;

import kiryasay.spring.models.Book;
import kiryasay.spring.models.Person;
import kiryasay.spring.repositories.PersonRepository;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final EntityManager entityManager;
    private final PersonRepository personRepository;
    @Autowired
    public PersonService(EntityManager entityManager, PersonRepository personRepository) {
        this.entityManager = entityManager;
        this.personRepository = personRepository;
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    @Transactional
    public void save(Person person)
    {
        personRepository.save(person);
    }

    public Person findById(int id){
        Session session = entityManager.unwrap(Session.class);
        Person person = session.get(Person.class, id);
        Hibernate.initialize(person.getBooks());
        return person;
    }
    @Transactional
    public void update(int id, Person person)
    {
        person.setId(id);
        personRepository.save(person);
    }
    @Transactional
    public void delete(int id)
    {
        personRepository.deleteById(id);
    }


}
