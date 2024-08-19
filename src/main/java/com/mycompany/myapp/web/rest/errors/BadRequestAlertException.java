package com.mycompany.myapp.web.rest.errors;

import java.io.Serial;
import lombok.Getter;
import org.springframework.core.NestedRuntimeException;

@Getter
public class BadRequestAlertException extends NestedRuntimeException {

  @Serial private static final long serialVersionUID = 1L;

  private final String errorCode;

  private final String errorDescription;

  public BadRequestAlertException(String errorCode, String errorDescription) {
    super(errorDescription);
    this.errorCode = errorCode;
    this.errorDescription = errorDescription;
  }
}
