package com.mycompany.myapp.web.rest.errors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/** Controller advice to translate the server side exceptions to client-friendly json structures. */
@ControllerAdvice
@Slf4j
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

  private final Environment env;

  @Value("${application.clientAppName}")
  private String applicationName;

  public ExceptionTranslator(Environment env) {
    this.env = env;
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleAnyException(Throwable ex, NativeWebRequest request) {
    return handleExceptionInternal(
        (Exception) ex,
        ex.getMessage(),
        buildHeaders(ex),
        HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()),
        request);
  }

  @Nullable
  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex,
      @Nullable Object body,
      HttpHeaders headers,
      HttpStatusCode statusCode,
      WebRequest request) {
    return super.handleExceptionInternal(ex, body, headers, statusCode, request);
  }

  private HttpHeaders buildHeaders(Throwable err) {
    return err instanceof BadRequestAlertException badRequestAlertException
        ? createFailureAlert(
            applicationName,
            true,
            badRequestAlertException.getErrorCode(),
            badRequestAlertException.getErrorDescription(),
            badRequestAlertException.getMessage())
        : null;
  }

  public static HttpHeaders createFailureAlert(
      String applicationName,
      boolean enableTranslation,
      String entityName,
      String errorKey,
      String defaultMessage) {
    log.error("Entity processing failed, {}", defaultMessage);
    String message = enableTranslation ? "error." + errorKey : defaultMessage;
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-" + applicationName + "-error", message);
    headers.add("X-" + applicationName + "-params", entityName);
    return headers;
  }
}
