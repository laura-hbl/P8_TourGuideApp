package rewards.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 * Contain methods that handles exceptions across the whole application.
 *
 * @author Laura Habdul
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * GlobalExceptionHandler logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles exception of the specific type MethodArgumentTypeMismatchException.
     *
     * @param ex      MethodArgumentTypeMismatchException object
     * @param request WebRequest object
     * @return ResponseEntity error response object and Http Status generated
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity handleTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {
        LOGGER.error("Request - FAILED : Invalid UUID");

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));
        errorDetails.setMessage("Invalid UUID string");

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exception of the specific type MissingPathVariableException.
     *
     * @param ex      IllegalArgumentException object
     * @param request WebRequest object
     * @return ResponseEntity error response object and Http Status generated
     */
    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(final MissingPathVariableException ex,
                                                               final HttpHeaders headers, final HttpStatus status,
                                                               final WebRequest request) {
        LOGGER.error("Request - FAILED : Missing path variable: {}", ex.getVariableName());

        String error = ex.getVariableName() + " parameter is missing";
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getLocalizedMessage(), error);

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
