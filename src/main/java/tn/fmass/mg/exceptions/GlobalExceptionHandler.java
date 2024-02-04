package tn.fmass.mg.exceptions;


import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;


@RestControllerAdvice
public class GlobalExceptionHandler {
    //List<String> details = new ArrayList<>();
    HashMap<String, String> erreurs = new HashMap<String, String>();
    // handleResourceNotFoundException : triggers when there is no resource with the specified ID in BDD
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<HashMap<String, String>> handleNotFoundException(Exception ex) {
       // details.clear();
        //details.add(ex.getMessage());
        erreurs.clear();
        erreurs.put("NOT FOUND ",ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(erreurs);
    }

    // handleMethodArgumentNotValid : triggers when @Valid fails
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HashMap<String, String>> handleArgumentNotValidException(MethodArgumentNotValidException ex) {
       // details.clear();
        erreurs.clear();
       // List<String> details ;
        ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .forEach(error ->erreurs.put(error.getField(),error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(erreurs);
                        /*ResponseEntityBuilder.builder()
                        .timestamp(LocalDateTime.now())
                        .message("Erreur dans la requête")
                        .status(HttpStatus.BAD_REQUEST)
                        .errors(details).build());*/

    }

    // handleConstraintViolationException : triggers when @Validated fails
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<HashMap<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
       // details.clear();
       // details.add(ex.getSQLException().getMessage());

        erreurs.clear();
        erreurs.put("DATA BASE ERROR ",ex.getSQLException().getMessage()+ex
        +ex.getConstraintName()+
                ex.getSQLException().getSQLState());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(erreurs);


    }

    // handleHttpMessageNotReadable : triggers when the JSON is malformed
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String, String>> handleJsonMalFormattedException(HttpMessageNotReadableException ex) {
        //details.clear();
        //details.add(ex.getMessage());
        erreurs.clear();
        erreurs.put("JSON MALFORMED ",ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(erreurs);
    }
    // handleHttpMediaTypeNotSupported : triggers when the JSON is invalid
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<HashMap<String, String>> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex) {


        //details.clear();
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

        //details.add(builder.toString());
        erreurs.clear();
        erreurs.put("JSON INVALID ",builder.toString());
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(erreurs);

    }
    // handleMissingServletRequestParameter : triggers when there are missing parameters
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<HashMap<String, String>> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex) {
        //details.clear();
        //details.add(ex.getParameterName() + " parameter is missing");
        erreurs.clear();
        erreurs.put("MISSING PARAMETER ",ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(erreurs);
    }
    // handleMethodArgumentTypeMismatch : triggers when a parameter's type does not match
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<HashMap<String, String>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        //details.clear();
        //details.add(ex.getMessage());
        erreurs.clear();
        erreurs.put("PARAMETER TYPE NOT MATCH ",ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(erreurs);
    }
    // handleNoHandlerFoundException : triggers when the handler method is invalid
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<HashMap<String, String>> handleNoHandlerFoundException(
            NoHandlerFoundException ex) {
        //details.clear();
        //details.add(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
        erreurs.clear();
        erreurs.put("METHOD INVALID ",String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(erreurs);

    }
}
