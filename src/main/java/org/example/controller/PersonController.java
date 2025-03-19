package org.example.controller;

import jakarta.validation.Valid;
import org.example.model.Book;
import org.example.model.Person;
import org.example.service.BookService;
import org.example.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;
    private final BookService bookService;

    public PersonController(PersonService personService, BookService bookService) {
        this.personService = personService;
        this.bookService = bookService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", personService.findAll());
        return "/people/index";
    }

    // Просмотр читателя
    @GetMapping("/{id}")
    public String show(Model model, @PathVariable Long id) {
        model.addAttribute("person", personService.findById(id));
        model.addAttribute("books", bookService.getAllBooksByOwnerId(id));

        return "people/show";
    }

    //Создание нового чеповека
    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("person", new Person());

        return "people/new";
    }

    @PostMapping
    public String post(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/new";
        }

        personService.save(person);
        return "redirect:/people";
    }
    //------------------------

    //Редактирование нового чеповека
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable Long id) {
        model.addAttribute("person", personService.findById(id));

        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String patch(@PathVariable Long id, @ModelAttribute("person") @Valid Person person,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }

        personService.update(id, person);

        return "redirect:/people/" + id;
    }
    //------------------------

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        personService.delete(id);
        bookService.deleteReader(id);
        
        return "redirect:/people";
    }
}
