package it.proconsole.library.video.adapter.jdbc.entity;

import java.util.Map;

public interface EntityWithId {
  Long id();

  Map<String, Object> data();
}
