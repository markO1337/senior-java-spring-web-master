package hu.ponte.hr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ContentTypeNotSupportedException extends RuntimeException {

    public ContentTypeNotSupportedException(String contentType) {
        super(String.format("ContentType not supported '%s'", contentType));
    }

}
