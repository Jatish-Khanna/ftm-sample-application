package com.mycompany.myapp.config;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Properties specific to Ftm Sample Application.
 *
 * <p>Properties are configured in the {@code application.yml} file.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

  private final Security security = new Security();
  private final CorsConfiguration cors = new CorsConfiguration();
  private final Http http = new Http();
  private final ApiDocs apiDocs = new ApiDocs();
  private final Logging logging = new Logging();

  private String clientAppName;

  @Getter
  @Setter
  public static class Security {
    private final OAuth2 oauth2 = new OAuth2();
    private String contentSecurityPolicy =
        """
                        default-src 'self'; frame-src 'self' data:; \
                        script-src 'self' 'unsafe-inline' 'unsafe-eval'; \
                        style-src 'self' 'unsafe-inline'; \
                        img-src 'self' data:; font-src 'self' data:
                        """;

    public static class OAuth2 {
      private final List<String> audience = new ArrayList<>();

      public OAuth2() {}

      public List<String> getAudience() {
        return Collections.unmodifiableList(this.audience);
      }

      public void setAudience(@NotNull List<String> audience) {
        this.audience.addAll(audience);
      }
    }
  }

  @Getter
  public static class Http {
    private final Cache cache = new Cache();

    public Http() {}

    @Getter
    @Setter
    public static class Cache {
      private int timeToLiveInDays = 1461;

      public Cache() {}
    }
  }

  @Getter
  @Setter
  public static class ApiDocs {
    private String title = "Application API";
    private String description = "API documentation";
    private String version = "0.0.1";
    private String termsOfServiceUrl;
    private String contactName;
    private String contactUrl;
    private String contactEmail;
    private String license;
    private String licenseUrl;
    private String[] defaultIncludePattern = new String[] {"/api/**"};
    private String[] managementIncludePattern = new String[] {"/management/**", "/actuator/**"};
  }

  @Getter
  @Setter
  public static class Logging {
    private boolean useJsonFormat = false;
  }
}
