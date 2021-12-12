package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.jdbc.repository.entity.EntityWithId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class DatabaseDaoTest<T extends EntityWithId> {
  DatabaseDao<T> dao;

  @Test
  void delete() {
    var entity = dao.save(anEntity());
    assertTrue(dao.findById(entity.id()).isPresent());

    dao.delete(entity);

    assertFalse(dao.findById(entity.id()).isPresent());
  }

  @Test
  void deleteAll() {
    var entities = dao.saveAll(List.of(anEntity(), anEntity()));
    var ids = entities.stream().map(EntityWithId::id).toList();
    assertFalse(dao.findAllById(ids).isEmpty());

    dao.deleteAll(entities);

    assertTrue(dao.findAllById(ids).isEmpty());
  }

  @Test
  void deleteById() {
    var entities = dao.saveAll(List.of(anEntity(), anEntity()));
    var ids = entities.stream().map(EntityWithId::id).toList();
    assertFalse(dao.findAllById(ids).isEmpty());

    dao.deleteAllById(ids);

    assertTrue(dao.findAllById(ids).isEmpty());
  }

  @Test
  void deleteAllById() {
    var entities = dao.saveAll(List.of(anEntity(), anEntity()));
    var ids = entities.stream().map(EntityWithId::id).toList();
    assertFalse(dao.findAllById(ids).isEmpty());

    dao.deleteAllById(ids);

    assertTrue(dao.findAllById(ids).isEmpty());
  }

  @Test
  void findAll() {
    var entities = dao.saveAll(List.of(anEntity(), anEntity()));

    var current = dao.findAll();

    assertEquals(entities, current);
  }

  @Test
  void save() {
    var anEntity = anEntity();
    assertFalse(dao.findById(anEntity.id()).isPresent());

    var savedEntity = dao.save(anEntity);

    var checkEntity = dao.findById(savedEntity.id());
    assertTrue(checkEntity.isPresent());
    assertEquals(savedEntity, checkEntity.get());
  }

  @Test
  void saveAll() {
    var entities = List.of(anEntity(), anEntity());
    var ids = entities.stream().map(EntityWithId::id).toList();
    assertTrue(dao.findAllById(ids).isEmpty());

    var savedEntities = dao.saveAll(entities);

    var savedIds = savedEntities.stream().map(EntityWithId::id).toList();
    var checkEntities = dao.findAllById(savedIds);
    assertEquals(savedEntities, checkEntities);
  }

  abstract T anEntity();

  @Nested
  class WhenFindAllById {
    @Test
    void listIfPresent() {
      var entities = dao.saveAll(List.of(anEntity(), anEntity()));
      var ids = entities.stream().map(EntityWithId::id).toList();

      var current = dao.findAllById(ids);

      assertEquals(entities, current);
    }

    @Test
    void emptyIfAbsent() {
      dao.save(anEntity());

      var current = dao.findAllById(List.of(Long.MAX_VALUE));

      assertTrue(current.isEmpty());
    }
  }

  @Nested
  class WhenFindById {
    @Test
    void entityIfPresent() {
      var entity = dao.save(anEntity());

      var current = dao.findById(entity.id());

      assertTrue(current.isPresent());
      assertEquals(entity, current.get());
    }

    @Test
    void emptyIfAbsent() {
      dao.save(anEntity());

      var current = dao.findById(Long.MAX_VALUE);

      assertFalse(current.isPresent());
    }
  }
}