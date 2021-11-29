package it.proconsole.videodb.model;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "genre")
@Entity
public class Genre {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "value", nullable = false)
  private String value;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Genre genre = (Genre) o;
    return Objects.equals(id, genre.id);
  }

  @Override
  public int hashCode() {
    return 0;
  }
}