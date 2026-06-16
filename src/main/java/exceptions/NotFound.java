package exceptions;

public class NotFound extends RuntimeException {
    public NotFound(long id) {
        super("L'evento con id " + id + " non è stato trovato!");
    }
}