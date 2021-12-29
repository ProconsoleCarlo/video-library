package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.jdbc.model.EntityWithId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class DatabaseDaoTest<T extends EntityWithId> {
  abstract DatabaseDao<T> dao();

  @AfterEach
  void tearDown() {
    dao().deleteAll();
  }

  @Test
  void delete() {
    var entity = dao().save(anEntity());
    var id = entity.id();
    assertNotNull(id);
    assertTrue(dao().findById(id).isPresent());

    dao().delete(entity);

    assertFalse(dao().findById(id).isPresent());
  }

  @Test
  void deleteAllTable() {
    dao().saveAll(List.of(anEntity(), anEntity()));
    assertFalse(dao().findAll().isEmpty());

    dao().deleteAll();

    assertTrue(dao().findAll().isEmpty());
  }

  @Test
  void deleteAll() {
    var entities = dao().saveAll(List.of(anEntity(), anEntity()));
    var ids = entities.stream().map(EntityWithId::id).toList();
    assertFalse(dao().findAllById(ids).isEmpty());

    dao().deleteAll(entities);

    assertTrue(dao().findAllById(ids).isEmpty());
  }

  @Test
  void deleteById() {
    var entity = dao().save(anEntity());
    var id = entity.id();
    assertNotNull(id);
    assertTrue(dao().findById(id).isPresent());

    dao().deleteById(id);

    assertFalse(dao().findById(id).isPresent());
  }

  @Test
  void deleteAllById() {
    var entities = dao().saveAll(List.of(anEntity(), anEntity()));
    var ids = entities.stream().map(EntityWithId::id).toList();
    assertFalse(dao().findAllById(ids).isEmpty());

    dao().deleteAllById(ids);

    assertTrue(dao().findAllById(ids).isEmpty());
  }

  @Test
  void findAll() {
    var entities = dao().saveAll(List.of(anEntity(), anEntity()));

    var current = dao().findAll();

    assertEquals(entities, current);
  }

  @Test
  void save() {
    var anEntity = anEntity();

    var savedEntity = dao().save(anEntity);

    var savedId = savedEntity.id();
    assertNotNull(savedId);
    var checkEntity = dao().findById(savedId);
    assertTrue(checkEntity.isPresent());
    assertEquals(savedEntity, checkEntity.get());

    var entityToUpdate = anEntityForUpdate(savedId);

    var updatedEntity = dao().save(entityToUpdate);

    assertEquals(savedEntity.id(), updatedEntity.id());
  }

  @Test
  void saveAll() {
    var entities = List.of(anEntity(), anEntity());
    var ids = entities.stream().map(EntityWithId::id).toList();
    assertTrue(dao().findAllById(ids).isEmpty());

    var savedEntities = dao().saveAll(entities);

    var savedIds = savedEntities.stream().map(EntityWithId::id).toList();
    var checkEntities = dao().findAllById(savedIds);
    assertEquals(savedEntities, checkEntities);

    var idToUpdate = savedEntities.get(0).id();
    assertNotNull(idToUpdate);
    var entityToUpdate = anEntityForUpdate(idToUpdate);
    var entitiesToUpdate = List.of(entityToUpdate);

    var updatedEntities = dao().saveAll(entitiesToUpdate);

    assertEquals(entityToUpdate.id(), updatedEntities.get(0).id());
  }

  @Nested
  class WhenFindAllById {
    @Test
    void listIfPresent() {
      var entities = dao().saveAll(List.of(anEntity(), anEntity()));
      var ids = entities.stream().map(EntityWithId::id).toList();

      var current = dao().findAllById(ids);

      assertEquals(entities, current);
    }

    @Test
    void emptyIfAbsent() {
      var current = dao().findAllById(List.of(Long.MAX_VALUE));

      assertTrue(current.isEmpty());
    }
  }

  @Nested
  class WhenFindById {
    @Test
    void entityIfPresent() {
      var entity = dao().save(anEntity());
      var id = entity.id();
      assertNotNull(id);
      var current = dao().findById(id);

      assertTrue(current.isPresent());
      assertEquals(entity, current.get());
    }

    @Test
    void emptyIfAbsent() {
      var current = dao().findById(Long.MAX_VALUE);

      assertFalse(current.isPresent());
    }
  }

  abstract T anEntity();

  abstract T anEntityForUpdate(Long id);
}