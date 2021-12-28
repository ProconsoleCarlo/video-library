package it.proconsole.library.video.adapter.jdbc.model;

import org.springframework.lang.Nullable;

import java.util.Map;

public interface EntityWithId {
  @Nullable
  Long id();

  Map<String, Object> data();
}
