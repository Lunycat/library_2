package org.example.service;

import org.example.model.Book;
import org.example.model.Person;
import org.example.repository.BookRepository;
import org.example.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(Long id, Book book) {
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void addReader(Long personId, Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        Person person = personRepository.findById(personId).orElse(null);
        book.setOwner(person);
        bookRepository.save(book);
    }

    @Transactional
    public void deleteReader(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        book.setOwner(null);
        bookRepository.save(book);
    }
}
