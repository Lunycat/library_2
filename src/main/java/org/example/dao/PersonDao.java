package org.example.dao;

import org.example.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getPeople() {
        return jdbcTemplate.query("SELECT * FROM people", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person getPerson(Long id) {
        return jdbcTemplate.query("SELECT * FROM people WHERE id = ?",
                new BeanPropertyRowMapper<>(Person.class), id).stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO people (full_name, age) VALUES (?, ?)",
                person.getFullName(), person.getAge());
    }

    public void update(Long id, Person person) {
        jdbcTemplate.update("UPDATE people SET full_name = ?, age = ? WHERE id = ?",
                person.getFullName(), person.getAge(), id);
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM people WHERE id = ?", id);
    }
}
