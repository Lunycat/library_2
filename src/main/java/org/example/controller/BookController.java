package org.example.controller;

import jakarta.validation.Valid;
import org.example.dao.BookDao;
import org.example.dao.PersonDao;
import org.example.model.Book;
import org.example.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDao bookDao;
    private final PersonDao personDao;

    @Autowired
    public BookController(BookDao bookDao, PersonDao personDao) {
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookDao.getBooks());
        return "books/index";
    }

    // Просмотр книги
    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        Book book = bookDao.getBook(id);
        model.addAttribute("book", book);
        /*if (book.getPersonId() == null) {
            model.addAttribute("people", personDao.getPeople());
            model.addAttribute("person", new Person());
        } else {
            model.addAttribute("person", personDao.getPerson(book.getPersonId()));
        }*/
        return "books/show";
    }

    @PatchMapping("/{bookId}/add_reader")
    public String addReader(@RequestParam Long personId, @PathVariable Long bookId) {
        bookDao.addReader(personId, bookId);
        return "redirect:/books/" + bookId;
    }

    @PatchMapping("/{bookId}/delete_reader")
    public String deleteReader(@PathVariable Long bookId) {
        bookDao.deleteReader(bookId);
        return "redirect:/books/" + bookId;
    }
    //------------------------

    //Создание новой книги
    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }
    //------------------------

    @PostMapping
    public String post(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }

        bookDao.save(book);
        return "redirect:/books";
    }
    //------------------------

    //Редактирование новой книги
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable Long id) {
        model.addAttribute("book", bookDao.getBook(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String patch(@PathVariable Long id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }

        bookDao.update(id, book);
        return "redirect:/books/" + book.getId();
    }
    //------------------------

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        bookDao.delete(id);
        return "redirect:/books";
    }
}
