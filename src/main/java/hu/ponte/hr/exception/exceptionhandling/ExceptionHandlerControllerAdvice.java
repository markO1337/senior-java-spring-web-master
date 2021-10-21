package hu.ponte.hr.exception.exceptionhandling;

import hu.ponte.hr.exception.ContentTypeNotSupportedException;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ExceptionResponse> maxUploadSizeExceededException(MaxUploadSizeExceededException exception, HttpServletRequest request) {
        String errorMessage = "File is too big. Max filesize: %s.";
        String maxSizeInMB = FileUtils.byteCountToDisplaySize(exception.getMaxUploadSize());
        ExceptionResponse response = ExceptionResponse.builder()
                .error(String.format(errorMessage, maxSizeInMB))
                .message("")
                .path(request.getRequestURI())
                .status(HttpStatus.PAYLOAD_TOO_LARGE.value())
                .timestamp(ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT))
                .build();
        return new ResponseEntity<>(response, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ExceptionHandler(ContentTypeNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> contentTypeNotSupportedException(ContentTypeNotSupportedException exception, HttpServletRequest request) {
        ExceptionResponse response = ExceptionResponse.builder()
                .error(exception.getMessage())
                .message("")
                .path(request.getRequestURI())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT))
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
