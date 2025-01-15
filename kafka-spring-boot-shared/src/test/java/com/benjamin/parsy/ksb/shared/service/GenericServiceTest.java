package com.benjamin.parsy.ksb.shared.service;

import com.benjamin.parsy.ksb.shared.AbstractTest;
import com.benjamin.parsy.ksb.shared.service.jpa.GenericService;
import com.benjamin.parsy.ksb.shared.testclass.Entity;
import com.benjamin.parsy.ksb.shared.testclass.SubEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GenericServiceTest extends AbstractTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GenericService<SubEntity> genericService;

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void save_EntityOk_ReturnEntity() {

        // Given
        Entity entity = jdbcTemplate.queryForObject("SELECT * FROM entity LIMIT 1",
                (rs, rowNum) -> {

                    Entity entityJdbc = new Entity();
                    entityJdbc.setId(rs.getLong("id"));
                    entityJdbc.setName(rs.getString("name"));

                    return entityJdbc;
                });
        SubEntity subEntity = new SubEntity("name", entity);

        // When
        subEntity = genericService.save(subEntity);

        // Then
        assertNotNull(subEntity.getId());

    }

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void findAll_EntitiessPresent_ReturnEntityList() {

        // When
        List<SubEntity> bookList = genericService.findAll();

        // Then
        assertEquals(2, bookList.size());

    }

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void findById_EntityPresent_ReturnEntity() {

        // Given
        Long id = jdbcTemplate.queryForObject("SELECT id FROM sub_entity LIMIT 1", Long.class);

        // When
        Optional<SubEntity> optionalBook = genericService.findById(id);

        // Then
        assertTrue(optionalBook.isPresent());

    }

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void deleteById_idExist_deleteOk() {

        // Given
        Long id = jdbcTemplate.queryForObject("SELECT id FROM sub_entity LIMIT 1", Long.class);

        // When and then
        boolean result = genericService.deleteById(id);

        // Then
        assertTrue(result);

    }

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void deleteById_idNotExist_deleteNotDone() {

        // Given
        long lastId = jdbcTemplate.queryForObject("SELECT id FROM sub_entity ORDER BY id DESC LIMIT 1;", Long.class);
        long idUnknown = lastId + 1;

        // When
        boolean result = genericService.deleteById(idUnknown);

        // Then
        assertFalse(result);

    }

}