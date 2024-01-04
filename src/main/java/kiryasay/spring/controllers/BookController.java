package kiryasay.spring.controllers;

import kiryasay.spring.dao.BookDao;
import kiryasay.spring.dao.PersonDAO;
import kiryasay.spring.models.Book;
import kiryasay.spring.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    private BookDao bookDao;
    private PersonDAO personDAO;
    @Autowired
    public BookController(BookDao bookDao, PersonDAO personDAO){
        this.bookDao = bookDao;
        this.personDAO = personDAO;
    }
    @GetMapping()
    public String allBooks(Model model)
    {
        model.addAttribute("books", bookDao.allBook());
        return "book/books";
    }
    @GetMapping("/{id}")
    public String bookInfo(@PathVariable("id") int id,
                           Model model){
        model.addAttribute("book", bookDao.show(id));
        model.addAttribute("owner", personDAO.show(bookDao.show(id).getOwnerId()));
        model.addAttribute("person", new Person());
        model.addAttribute("persons", personDAO.allPeople());
        return "book/bookInfo";
    }
    @GetMapping("/new")
    public String newBook(Model model)
    {
        model.addAttribute("book", new Book());
        return "book/newBook";
    }
    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
            return "book/newBook";
        bookDao.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model)
    {
        model.addAttribute("book", bookDao.show(id));
        return "book/editBook";
    }
    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") int id,
                       @ModelAttribute("book") @Valid Book book,
                       BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "book/editBook";
        bookDao.update(id, book);
        return "redirect:/books/{id}";
    }
    @PostMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id)
    {
        bookDao.release(id);
        return "redirect:/books/{id}";
    }
    @PostMapping("/{id}/addOwner")
    public String addOwner(@PathVariable("id") int id, @ModelAttribute("person") Person person)
    {
        bookDao.addOwner(id, person.getId());
        return "redirect:/books/{id}";
    }
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookDao.delete(id);
        return "redirect:/books";
    }
}
