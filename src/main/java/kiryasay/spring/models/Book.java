package kiryasay.spring.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

public class Book {
    @NotNull()
    @Pattern(regexp = "[А-я ][а-я ]+")
    private String title;
    @Pattern( regexp = "[А-Я][а-я]+ [А-Я][а-я]+", message = "Введите фамилию и имя автора")
    private String author;

    private int age;

    int id;
    int ownerId;

    public Book(String title, String author, int age, int id, int ownerId) {
        this.title = title;
        this.author = author;
        this.age = age;
        this.id = id;
        this.ownerId = ownerId;
    }
    public Book() {}

    @Override
    public String toString() {
        return title + ", "+ author + ", " + age + "\n";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
    public static String createList(List<Book> books)
    {
        if(books.isEmpty())
            return "Он не взял ни одной книги";
        else{
            StringBuilder result = new StringBuilder();
            for (Book book : books) {
                result.append(book.toString());
            }
            return result.toString();
        }
    }
}
