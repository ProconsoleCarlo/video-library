package it.proconsole.library.video.adapter.xlsx.repository.workbook;

import it.proconsole.library.video.adapter.xlsx.exception.InvalidXlsxFileException;
import it.proconsole.library.video.adapter.xlsx.model.FilmReviewRow;
import it.proconsole.library.video.adapter.xlsx.model.FilmRow;
import it.proconsole.library.video.adapter.xlsx.repository.workbook.adapter.FilmReviewValueAdapter;
import it.proconsole.library.video.adapter.xlsx.repository.workbook.adapter.FilmValueAdapter;
import it.proconsole.library.video.adapter.xlsx.repository.workbook.adapter.GenreValueAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilmWorkbookRepositoryTest {
  private final GenreValueAdapter genreValueAdapter = new GenreValueAdapter();
  private final FilmReviewValueAdapter filmReviewAdapter = new FilmReviewValueAdapter();
  private final FilmValueAdapter filmValueAdapter = new FilmValueAdapter(genreValueAdapter, filmReviewAdapter);
  private final FilmWorkbookRepository repository = new FilmWorkbookRepository(
          "./src/test/resources/TestCatalogoFilm.xlsx",
          filmValueAdapter
  );

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
    var repository = new FilmWorkbookRepository(path, filmValueAdapter);

    assertThrows(InvalidXlsxFileException.class, repository::findAll);
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
    return new FilmRow("Title", 2021, List.of("Azione"), List.of(new FilmReviewRow(LocalDateTime.now(), 8, "Comment")));
  }

  private FilmRow anEntityForUpdate(Long id) {
    return new FilmRow(id,
            "Title",
            2021,
            List.of("Commedia"),
            List.of(new FilmReviewRow(1L, LocalDateTime.now(), 8, "Comment"), new FilmReviewRow(LocalDateTime.now(), 10, "Another comment"))
    );
  }
}