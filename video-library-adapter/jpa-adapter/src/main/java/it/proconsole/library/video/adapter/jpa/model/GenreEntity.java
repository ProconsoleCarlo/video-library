package it.proconsole.library.video.adapter.jpa.model;

import it.proconsole.library.video.core.model.GenreEnum;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Table(name = "genre")
@Entity
public class GenreEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "value", nullable = false)
  @Enumerated(EnumType.STRING)
  private GenreEnum value;

  public GenreEntity() {
  }

  public GenreEntity(Long id, GenreEnum value) {
    this.id = id;
    this.value = value;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public GenreEnum getValue() {
    return value;
  }

  public void setValue(GenreEnum value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    GenreEntity genre = (GenreEntity) o;
    return Objects.equals(id, genre.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}