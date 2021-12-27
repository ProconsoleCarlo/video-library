package it.proconsole.library.video.rest.exception;

import it.proconsole.library.video.core.repository.Protocol;

public class UnknownProtocolException extends RuntimeException {
  public UnknownProtocolException(String protocol) {
    super("Unknown protocol " + protocol + " received");
  }

  public UnknownProtocolException(Protocol protocol) {
    super("Action not allowed for protocol " + protocol);
  }
}
