package hu.ponte.hr.exception;

import hu.ponte.hr.service.signer.model.SignType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class FileSignatureException extends RuntimeException {

    public FileSignatureException(SignType signType, Throwable cause) {
        super(String.format("File signature failed with algorithm: {%s}", signType), cause);
    }

}
