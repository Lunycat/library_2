package org.example.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Person {

    private Long id;

    @Pattern(regexp = "[А-Я]\\W+\\s[А-Я]\\W+\\s[А-Я]\\W+",
            message = "Полное имя должно соотвествовать следующему патерну: Имя Фамилия Отчество")
    private String fullName;

    @Min(value = 1900, message = "Год рождения не может быть меньше 1900 года")
    private Integer age;
}
