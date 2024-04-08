package mate.academy.rickandmorty.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class HttpStatusCodeNotOkException extends RuntimeException {
    private final HttpStatusCode statusCode;
    private final String responseBody;

    public HttpStatusCodeNotOkException(HttpStatusCode statusCode, String responseBody) {
        super("HTTP status code is not OK: " + statusCode + ", response body: "
                + responseBody);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }
}
