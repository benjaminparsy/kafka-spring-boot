package com.benjamin.parsy.ksb.shared.service;

import com.benjamin.parsy.ksb.shared.AbstractTest;
import com.benjamin.parsy.ksb.shared.exception.GlobalException;
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
    void deleteById_deleteOk() {

        // Given
        Long id = jdbcTemplate.queryForObject("SELECT id FROM sub_entity LIMIT 1", Long.class);

        // When and then
        assertDoesNotThrow(() -> genericService.deleteById(id));

    }

    @Sql(scripts = {
            "classpath:purge.sql",
            "classpath:data.sql"
    })
    @Test
    void deleteById_idNotExist_throwException() {

        // Given
        long lastId = jdbcTemplate.queryForObject("SELECT id FROM sub_entity ORDER BY id DESC LIMIT 1;", Long.class);
        long idUnknown = lastId + 1;

        // When
        GlobalException exception = assertThrows(GlobalException.class,
                () -> genericService.deleteById(idUnknown));

        // Then
        String msg = String.format("[IE1] Item with id %s cannot be found in database", idUnknown);
        assertEquals(msg, exception.getMessage());

    }

}