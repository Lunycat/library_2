package org.example.service;

import org.example.model.Book;
import org.example.model.Person;
import org.example.repository.BookRepository;
import org.example.repository.PersonRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    public List<Book> findAll(int page, int itemPerPage, boolean sortYear) {
        List<Book> result;
        if (sortYear) {
            result = bookRepository.findAll(PageRequest.of(page, itemPerPage, Sort.by("year"))).getContent();
        } else {
            result = bookRepository.findAll(PageRequest.of(page, itemPerPage)).getContent();
        }
        return result;
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
        book.setAbsenceSince(LocalDateTime.now());
        bookRepository.save(book);
    }

    @Transactional
    public void deleteReader(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        book.setOwner(null);
        book.setAbsenceSince(null);
        bookRepository.save(book);
    }

    public List<Book> getAllBooksByOwnerId(Long personId) {
        Person person = personRepository.findById(personId).orElse(null);
        return person.getBooks();
    }

    public Book getSearchBook(String search) {
        return bookRepository.findByTitleStartingWithIgnoreCase(search);
    }

    public Map<Long, Boolean> findOverdueBooks(List<Book> books) {
        Map<Long, Boolean> overdueBooksId = new HashMap<>();

        for (Book book : books) {
            long daysBetween = ChronoUnit.DAYS.between(LocalDateTime.now(), book.getAbsenceSince()) + 1;

            if (daysBetween > 10) {
                overdueBooksId.put(book.getId(), Boolean.TRUE);
            } else {
                overdueBooksId.put(book.getId(), Boolean.FALSE);
            }
        }

        return overdueBooksId;
    }
}
