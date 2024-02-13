package kiryasay.spring.controllers;

import kiryasay.spring.dao.BookDao;
import kiryasay.spring.dao.PersonDAO;
import kiryasay.spring.models.Book;
import kiryasay.spring.models.Person;
import kiryasay.spring.services.BookService;
import kiryasay.spring.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/books")
public class BookController {
    private BookDao bookDao;
    private PersonDAO personDAO;

    private BookService bookService;
    private PersonService personService;
    @Autowired
    public BookController(BookDao bookDao, PersonDAO personDAO, BookService bookService, PersonService personService){
        this.bookDao = bookDao;
        this.personDAO = personDAO;
        this.bookService = bookService;
        this.personService = personService;
    }
    @GetMapping()
    public String allBooks(@RequestParam(name = "page", required = false) String page,
            @RequestParam(name = "books_per_page", required = false) String booksPerPage,
            @RequestParam(name = "sort_by_year", required = false) boolean sort,
                           Model model)
    {
        System.out.println(sort);
        System.out.println(page);
        System.out.println(booksPerPage);
        if(page == null && booksPerPage == null && !sort) {
            model.addAttribute("books", bookService.findAll());
            return "book/books";
        }
        else if (!sort && page != null && booksPerPage != null ) {
            model.addAttribute("books", bookService.findAll(Integer.parseInt(page), Integer.parseInt(booksPerPage)));
            return "book/books";
        }
        else if(sort && page == null && booksPerPage == null){
            System.out.println("Зашел");
            model.addAttribute("books", bookService.findAll(true));
            return "book/books";
        }
        return "book/books";

    }
    @GetMapping("/{id}")
    public String bookInfo(@PathVariable("id") int id,
                           Model model){
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("person", new Person());
        model.addAttribute("persons", personService.findAll());
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
        bookService.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model)
    {
        model.addAttribute("book", bookService.findById(id));
        return "book/editBook";
    }
    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") int id,
                       @ModelAttribute("book") @Valid Book book,
                       BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "book/editBook";
        bookService.update(id, book);
        return "redirect:/books/{id}";
    }
    @PostMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id)
    {
        bookService.release(id);
        return "redirect:/books/{id}";
    }
    @PostMapping("/{id}/addOwner")
    public String addOwner(@PathVariable("id") int id, @ModelAttribute("person") Person person)
    {
        bookService.setOwner(id, person);
        return "redirect:/books/{id}";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "bookTitle", required = false) String title,
                         Model model)
    {
        if(title != null)
            model.addAttribute("book", bookService.findByTitle(title));
        else{
            Book book = new Book();
            book.setTitle("undefined");
            model.addAttribute("book", book);
        }
        return "book/bookSearch";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookService.delete(id);
        return "redirect:/books";
    }
}
