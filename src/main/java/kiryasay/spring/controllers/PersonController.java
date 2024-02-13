package kiryasay.spring.controllers;

import kiryasay.spring.dao.BookDao;
import kiryasay.spring.dao.PersonDAO;
import kiryasay.spring.models.Person;
import kiryasay.spring.services.BookService;
import kiryasay.spring.services.PersonService;
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

    private PersonService personService;
    private BookService bookService;

    @Autowired
    public PersonController(PersonDAO personDAO, BookDao bookDao, PersonService personService, BookService bookService)
    {   this.personDAO = personDAO;
        this.bookDao = bookDao;
        this.personService = personService;
        this.bookService = bookService;
    }
    @GetMapping()
    public String persons(Model model){
        model.addAttribute("persons", personService.findAll());
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
        model.addAttribute("person", personService.findById(id));
        model.addAttribute("books", bookService.findByOwner(personService.findById(id)) );
        return "person/personInfo";
    }
    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id,
                             Model model)
    {
        model.addAttribute("person", personService.findById(id));
        return "person/editPerson";
    }
    @PatchMapping("/{id}")
    public String updateEdit(@ModelAttribute("person") @Valid Person person,
                             BindingResult bindingResult,
                             @PathVariable("id") int id){
        if(bindingResult.hasErrors())
            return "person/editPerson";
        personService.update(id, person);
        return "redirect:/people/{id}";
    }
    @PostMapping()
    public String createPerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "person/newPerson";
        personService.save(person);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        return "redirect:/people";
    }
}
