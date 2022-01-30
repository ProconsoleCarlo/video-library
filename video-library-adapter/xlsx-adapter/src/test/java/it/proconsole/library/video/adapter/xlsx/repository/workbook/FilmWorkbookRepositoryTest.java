package it.proconsole.library.video.adapter.xlsx.repository.workbook;

import it.proconsole.library.video.adapter.xlsx.exception.InvalidXlsxFileException;
import it.proconsole.library.video.adapter.xlsx.model.FilmRow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;
import java.util.List;

import static it.proconsole.library.video.adapter.xlsx.model.FilmRow.Builder.aFilmRow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilmWorkbookRepositoryTest {
  private final FilmWorkbookRepository repository = new FilmWorkbookRepository("./src/test/resources/TestCatalogoFilm.xlsx", new GenreValueAdapter());

  @BeforeEach
  void setUp() {
    repository.deleteAll();
  }

  @Test
  void delete() {
    var entity = repository.save(anEntity());
    var id = entity.id();
    assertNotNull(id);
    assertTrue(repository.findById(id).isPresent());

    repository.delete(entity);

    assertFalse(repository.findById(id).isPresent());
  }

  @Test
  void deleteAllRows() {
    repository.saveAll(List.of(anEntity(), anEntity()));
    assertFalse(repository.findAll().isEmpty());

    repository.deleteAll();

    assertTrue(repository.findAll().isEmpty());
  }

  @Test
  void deleteAll() {
    var entities = repository.saveAll(List.of(anEntity(), anEntity(), anEntity()));
    var ids = entities.stream().skip(1).map(FilmRow::id).toList();
    assertFalse(repository.findAllById(ids).isEmpty());

    repository.deleteAll(entities);

    assertTrue(repository.findAllById(ids).isEmpty());
  }

  @Test
  void deleteById() {
    var entities = repository.saveAll(List.of(anEntity(), anEntity()));
    var id = entities.get(0).id();
    assertNotNull(id);
    assertTrue(repository.findById(id).isPresent());

    repository.deleteById(id);

    assertFalse(repository.findById(id).isPresent());
  }

  @Test
  void deleteAllById() {
    var entities = repository.saveAll(List.of(anEntity(), anEntity(), anEntity()));
    var ids = entities.stream().skip(1).map(FilmRow::id).toList();
    assertFalse(repository.findAllById(ids).isEmpty());

    repository.deleteAllById(ids);

    assertTrue(repository.findAllById(ids).isEmpty());
  }

  @Test
  void findAll() {
    var entities = repository.saveAll(List.of(anEntity(), anEntity()));

    var current = repository.findAll();

    assertEquals(entities, current);
  }

  @Test
  void save() {
    var anEntity = anEntity();

    var savedEntity = repository.save(anEntity);

    var savedId = savedEntity.id();
    assertNotNull(savedId);
    var checkEntity = repository.findById(savedId);
    assertTrue(checkEntity.isPresent());
    assertEquals(savedEntity, checkEntity.get());

    var entityToUpdate = anEntityForUpdate(savedId);

    var updatedEntity = repository.save(entityToUpdate);

    assertEquals(savedEntity.id(), updatedEntity.id());
  }

  @Test
  void saveAll() {
    var entities = List.of(anEntity(), anEntity());
    var ids = entities.stream().map(FilmRow::id).toList();
    assertTrue(repository.findAllById(ids).isEmpty());

    var savedEntities = repository.saveAll(entities);

    var savedIds = savedEntities.stream().map(FilmRow::id).toList();
    var checkEntities = repository.findAllById(savedIds);
    assertEquals(savedEntities, checkEntities);

    var idToUpdate = savedEntities.get(0).id();
    assertNotNull(idToUpdate);
    var entityToUpdate = anEntityForUpdate(idToUpdate);
    var entitiesToUpdate = List.of(entityToUpdate);

    var updatedEntities = repository.saveAll(entitiesToUpdate);

    assertEquals(entityToUpdate.id(), updatedEntities.get(0).id());
  }

  @ParameterizedTest
  @ValueSource(strings = {"/invalidPath/TestCatalogoFilm.xlsx", "./src/test/resources/InvalidFile.xlsx"})
  void invalidXlsxFile(String path) {
    assertThrows(InvalidXlsxFileException.class, () -> new FilmWorkbookRepository(path, new GenreValueAdapter()).findAll());
  }

  @Nested
  class WhenFindAllById {
    @Test
    void listIfPresent() {
      var films = repository.saveAll(List.of(anEntity(), anEntity()));
      var ids = films.stream().map(FilmRow::id).toList();

      var current = repository.findAllById(ids);

      assertEquals(films, current);
    }

    @Test
    void emptyIfAbsent() {
      var current = repository.findAllById(List.of(Long.MAX_VALUE));

      assertTrue(current.isEmpty());
    }
  }

  @Nested
  class WhenFindById {
    @Test
    void entityIfPresent() {
      var entity = repository.save(anEntity());
      var id = entity.id();
      assertNotNull(id);
      var current = repository.findById(id);

      assertTrue(current.isPresent());
      assertEquals(entity, current.get());
    }

    @Test
    void emptyIfAbsent() {
      var current = repository.findById(Long.MAX_VALUE);

      assertFalse(current.isPresent());
    }
  }

  private FilmRow anEntity() {
    return aFilmRow("Title", 2021).build();
  }

  private FilmRow anEntityForUpdate(Long id) {
    return new FilmRow(id,
            "Title",
            2021,
            List.of("Commedia"),
            Collections.emptyList()
    );
  }
}