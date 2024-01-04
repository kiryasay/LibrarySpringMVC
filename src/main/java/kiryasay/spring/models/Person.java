package kiryasay.spring.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class Person {
    @Pattern( regexp = "[А-Я][а-я]+ [А-Я][а-я]+ [А-Я][а-я]+", message = "Укажите полное ФИО")
    private String name;

    //@Pattern(regexp = "\\d{4}", message = "It`s not your date of birthday")
    @Min(value = 1000, message = "Укажите год рождения")
    private int age;

    int id;

    public Person(String name, int age, int id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    public Person() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                '}';
    }
}
