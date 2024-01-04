package kiryasay.spring.util;

import kiryasay.spring.dao.PersonDAO;
import kiryasay.spring.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;
    @Autowired
    public PersonValidator(PersonDAO personDAO)
    {
        this.personDAO = personDAO;
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        //посмотреть есть ли человек с таким же email в бд
        if(personDAO.check(person.getName(), person.getAge()).isPresent()){//если не null значит такой email уже есть
            errors.rejectValue("name", "", "Пользователь уже зарегестрирован");
        }

    }
}
