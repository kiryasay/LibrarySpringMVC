package kiryasay.spring.dao;

import kiryasay.spring.models.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
        Person person = new Person();

        person.setId(resultSet.getInt("person_id"));
        person.setName(resultSet.getString("person_name"));
        person.setAge(resultSet.getInt("person_age"));
        return person;
    }
}