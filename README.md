# ftmSampleApplication

## Project Structure

## Development



Some of Keycloak configuration is now done in build time and the other part before running the app, here is the [list](https://www.keycloak.org/server/all-config) of all build and configuration options.

Before moving to production, please make sure to follow this [guide](https://www.keycloak.org/server/configuration) for better security and performance.

Also, you should never use `start-dev` nor `KC_DB=dev-file` in production.

When using Kubernetes, importing should be done using init-containers (with a volume when using `db=dev-file`).

```bash
./mvnw generate-sources
```

Then implements the generated delegate classes with `@Service` classes.

To edit the `api.yml` definition file, you can use a tool such as [Swagger-Editor](). Start a local instance of the swagger-editor using docker by running: `docker compose -f src/main/docker/swagger-editor.yml up -d`. The editor will then be reachable at [http://localhost:7742](http://localhost:7742).

Refer to [Doing API-First development][] for more details.
The build system will install automatically the recommended version of Node and npm.

We provide a wrapper to launch npm.
You will only need to run this command when dependencies change in [package.json](package.json).

## Building for production

### Packaging as jar

To build the final jar and optimize the ftmSampleApplication application for production, run:

```
./mvnw -Pprod clean verify
```

This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
To ensure everything worked, run:

```
java -jar target/*.jar
```

Then navigate to [http://localhost:8081](http://localhost:8081) in your browser.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

```
./mvnw -Pprod,war clean verify
```

## Testing

### Spring Boot tests

To launch your application's tests, run:

```
./mvnw verify
```

Sonar analysis:

```
./mvnw -Pprod clean verify sonar:sonar -Dsonar.login=admin -Dsonar.password=admin
```

If you need to re-run the Sonar phase, please be sure to specify at least the `initialize` phase since Sonar properties are loaded from the sonar-project.properties file.

```
./mvnw initialize sonar:sonar -Dsonar.login=admin -Dsonar.password=admin
```

Additionally, Instead of passing `sonar.password` and `sonar.login` as CLI arguments, these parameters can be configured from [sonar-project.properties](sonar-project.properties) as shown below:

```
sonar.login=admin
sonar.password=admin
```