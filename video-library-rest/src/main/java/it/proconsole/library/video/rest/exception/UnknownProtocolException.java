package it.proconsole.library.video.rest.exception;

public class UnknownProtocolException extends RuntimeException {
  public UnknownProtocolException(String protocol) {
    super("Unknown protocol " + protocol + " received");
  }
}
