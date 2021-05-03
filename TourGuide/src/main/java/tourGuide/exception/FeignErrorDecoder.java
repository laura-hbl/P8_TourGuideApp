package tourGuide.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Handles errors that takes place when using Feign client in Microservices communication.
 *
 * @author Laura Habdul
 */
public class FeignErrorDecoder implements ErrorDecoder {

    /**
     * FeignErrorDecoder logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(FeignErrorDecoder.class);

    /**
     * Default ErrorDecoder.
     */
    private final ErrorDecoder defaultErrorDecoder = new Default();

    /**
     * Permits to throw custom exceptions depending on the request status code.
     *
     * @param methodKey contains a Feign client class name and a method name
     * @param response  Response object
     * @return A custom exception if one of the conditions is met, otherwise a FeignException
     */
    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.status() == 404) {
            LOGGER.error("Error took place when using Feign client to send HTTP Request. Status code "
                    + response.status() + ", methodKey = " + methodKey);
            return new ResourceNotFoundException("Resource not found");
        }

        return defaultErrorDecoder.decode(methodKey, response);
    }
}