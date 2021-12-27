package it.proconsole.library.video.adapter.jdbc.repository.entity;

import java.util.Map;

public interface EntityWithId {
  Long id();

  Map<String, Object> data();
}
