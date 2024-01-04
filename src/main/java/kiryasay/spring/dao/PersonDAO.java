package kiryasay.spring.dao;

import kiryasay.spring.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> allPeople(){ return jdbcTemplate.query("SELECT * FROM Person", new PersonMapper());}

    public Person show(int id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id = ?", new Object[]{id}, new PersonMapper()).stream().findAny().orElse(null);
    }
    public Optional<Person> check(String name, int age){
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_name = ? AND person_age = ?", new Object[]{name, age}, new  BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
    public void delete(int id) { jdbcTemplate.update("DELETE FROM Person WHERE person_id = ?", id);}
    public void update(int id, Person person) {jdbcTemplate.update("UPDATE Person SET person_name = ?, person_age = ? WHERE person_id = ?", person.getName(), person.getAge(), id );}
    public void save(Person person)
    {
        jdbcTemplate.update("INSERT INTO Person(person_name, person_age) VALUES(?,?)",
                person.getName(), person.getAge());
    }

}
