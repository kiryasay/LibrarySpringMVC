package kiryasay.spring.controllers;

import kiryasay.spring.dao.BookDao;
import kiryasay.spring.dao.PersonDAO;
import kiryasay.spring.models.Book;
import kiryasay.spring.models.Person;
import kiryasay.spring.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PersonController {
    private PersonDAO personDAO;

    private BookDao bookDao;
    private PersonValidator personValidator;

    @Autowired
    public PersonController(PersonDAO personDAO, PersonValidator personValidator, BookDao bookDao)
    {   this.personDAO = personDAO;
        this.personValidator = personValidator;
        this.bookDao = bookDao;
    }
    @GetMapping("/head")
    public String header()
    {
        return "assist/header";
    }
    @GetMapping()
    public String persons(Model model){
        model.addAttribute("persons", personDAO.allPeople());
        return "person/persons";
    }
    @GetMapping("/new")
    public String create(Model model){
        model.addAttribute("person", new Person());
        return "person/newPerson";
    }
    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id,
                             Model model){
        model.addAttribute("person", personDAO.show(id));
       // model.addAttribute("books", Book.createList(bookDao.personBooks(id)));
        model.addAttribute("books", bookDao.personBooks(id));
        System.out.println(bookDao.personBooks(id));
        return "person/personInfo";
    }
    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id,
                             Model model)
    {
        model.addAttribute("person", personDAO.show(id));
        return "person/editPerson";
    }
    @PatchMapping("/{id}")
    public String updateEdit(@ModelAttribute("person") @Valid Person person,
                             BindingResult bindingResult,
                             @PathVariable("id") int id){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "person/editPerson";
        personDAO.update(id, person);
        return "redirect:/people/{id}";
    }
    @PostMapping()
    public String createPerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "person/newPerson";
        personDAO.save(person);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people";
    }
}
