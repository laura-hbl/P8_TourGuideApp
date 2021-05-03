package tourGuide.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
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
     * Handles exception of the specific type ResourceNotFoundException.
     *
     * @param ex      ResourceNotFoundException object
     * @param request WebRequest object
     * @return ResponseEntity error response object and Http Status generated
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity handleNotFound(final ResourceNotFoundException ex, final WebRequest request) {
        LOGGER.error("Request - FAILED :", ex);
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles exception of the specific type DataAlreadyRegisteredException.
     *
     * @param ex      DataAlreadyRegisteredException object
     * @param request WebRequest object
     * @return ResponseEntity error response object and Http Status generated
     */
    @ExceptionHandler(DataAlreadyRegisteredException.class)
    public ResponseEntity handleConflict(final DataAlreadyRegisteredException ex, final WebRequest request) {
        LOGGER.error("Request - FAILED :", ex);
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    /**
     * Handles exception of the specific type BadRequestException.
     *
     * @param ex      BadRequestException object
     * @param request WebRequest object
     * @return ResponseEntity error response object and Http Status generated
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity handleBadRequest(final BadRequestException ex, final WebRequest request) {
        LOGGER.error("Request - FAILED :", ex);
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exception of the specific type InternalError.
     *
     * @param ex InternalError object
     * @param request WebRequest object
     * @return ResponseEntity error response object and Http Status generated
     */
//     @ExceptionHandler(InternalError.class)
//    public ResponseEntity handleServerError(final InternalError ex, final WebRequest request) {
//        LOGGER.error("Request - FAILED : Internal Server error");
//        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
//                request.getDescription(false));
//
//        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    /**
     * Handles exception of the specific type MissingServletRequestParameterException.
     *
     * @param ex      MissingServletRequestParameterException object
     * @param request WebRequest object
     * @return ResponseEntity error response object and Http Status generated
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status,
            final WebRequest request) {
        LOGGER.error("Request - FAILED : Missing parameter: {}", ex.getParameterName());

        final String error = ex.getParameterName() + " parameter is missing";
        final ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}