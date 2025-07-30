package br.com.rodrigofolha.delivery.delivery_tracking.domain.exception;

public class DomainException extends RuntimeException {
    public DomainException() {

    }

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
