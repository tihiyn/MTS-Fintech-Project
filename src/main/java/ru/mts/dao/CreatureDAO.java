package ru.mts.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.mts.model.Animal;

import java.util.List;

@Repository
public class CreatureDAO {
    private final JdbcTemplate jdbcTemplate;

    public CreatureDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Animal> listCreatures() {
        return jdbcTemplate.query("SELECT * FROM animals.creature", new BeanPropertyRowMapper<>(Animal.class));
    }
}
