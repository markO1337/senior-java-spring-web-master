package hu.ponte.hr.exception.exceptionhandling;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionResponse {

    private String error;
    private String message;
    private String path;
    private int status;
    private String timestamp;

}
