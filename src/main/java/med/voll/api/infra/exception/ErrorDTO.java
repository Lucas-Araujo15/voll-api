package med.voll.api.infra.exception;

import java.time.LocalDateTime;

public record ErrorDTO(
        String error,
        String message,
        LocalDateTime date
) {
    public ErrorDTO(String error, String message, LocalDateTime date) {
        this.error = error;
        this.message = message;
        this.date = date;
    }
}
