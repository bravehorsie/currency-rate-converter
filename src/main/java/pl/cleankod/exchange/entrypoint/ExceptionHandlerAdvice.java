package pl.cleankod.exchange.entrypoint;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.cleankod.exchange.core.usecase.CurrencyConversionException;
import pl.cleankod.exchange.entrypoint.model.ApiError;

import java.util.Optional;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    private static final String INTERNAL_SERVER_ERROR_MSG = "Internal server error, please contact the technical support";

    @ExceptionHandler({
            CurrencyConversionException.class,
            IllegalArgumentException.class
    })
    protected ResponseEntity<ApiError> handleBadRequest(CurrencyConversionException ex) {
        return ResponseEntity.badRequest().body(new ApiError(ex.getMessage()));
    }

    @ExceptionHandler(FeignException.class)
    protected ResponseEntity<ApiError> handleNbpClientError(FeignException ex) {
        HttpStatus status = Optional.ofNullable(HttpStatus.resolve(ex.status())).orElse(HttpStatus.INTERNAL_SERVER_ERROR);
        switch (status) {
            case NOT_FOUND: return ResponseEntity.notFound().build();
            default:
                log.info("Error calling NBP API", ex);
                return ResponseEntity.internalServerError().body(new ApiError(INTERNAL_SERVER_ERROR_MSG));
        }

    }

}
