package ru.mts.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.mts.models.Creature;

import java.util.List;

@Repository
public class CreatureDAO {
    private final JdbcTemplate jdbcTemplate;

    public CreatureDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Creature> listCreatures() {
        return jdbcTemplate.query("SELECT * FROM animals.creature", new BeanPropertyRowMapper<>(Creature.class));
    }
}
