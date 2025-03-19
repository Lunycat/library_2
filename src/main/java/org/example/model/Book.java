package org.example.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Book {

    private Long id;

    private Long personId;

    @NotNull
    private String title;

    @NotNull
    private String author;

    @NotNull
    private Integer year;
}
