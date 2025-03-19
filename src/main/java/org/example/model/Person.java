package org.example.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "people")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Pattern(regexp = "[А-Я]\\W+\\s[А-Я]\\W+\\s[А-Я]\\W+",
            message = "Полное имя должно соотвествовать следующему патерну: Имя Фамилия Отчество")
    @Column(name = "full_name")
    private String fullName;

    @Min(value = 1900, message = "Год рождения не может быть меньше 1900 года")
    @Column(name = "age")
    private Integer age;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Book> books;
}
