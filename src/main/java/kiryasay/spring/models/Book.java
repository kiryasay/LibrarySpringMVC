package kiryasay.spring.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Book")
public class Book {
    @NotNull()
    @Pattern(regexp = "[А-я ][а-я ]+")
    @Column(name = "book_title")
    private String title;
    @Pattern( regexp = "[А-Я][а-я]+ [А-Я][а-я]+", message = "Введите фамилию и имя автора")
    @Column(name = "book_author")
    private String author;

    @Column(name = "book_age")
    private int age;

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne()
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;


    public Book(String title, String author, int age, int id) {
        this.title = title;
        this.author = author;
        this.age = age;
        this.id = id;
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

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (age != book.age) return false;
        if (id != book.id) return false;
        if (!Objects.equals(title, book.title)) return false;
        return Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + id;
        return result;
    }
}
