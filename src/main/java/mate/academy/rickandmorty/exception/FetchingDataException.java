package mate.academy.rickandmorty.exception;

public class FetchingDataException extends RuntimeException {
    public FetchingDataException(String message, Throwable e) {
        super(message, e);
    }
}
