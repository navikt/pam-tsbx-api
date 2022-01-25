# Token sandbox API component

Access to this demo API on behalf of end user requires a dedicated token
obtained through token exchange. An ID-token obtained from ID-porten is
exchanged for an access token to this particular API, on behalf of the logged in
user.

See [README from pam-tsbx-front][1] for guide to complete local setup.

This app uses the [`token-support`][2] project to do REST endpoint protection
and access token validation, more specifically the `token-validation-spring`
library.

[1]: https://github.com/navikt/pam-tsbx-front#running-locally
[2]: https://github.com/navikt/token-support#token-validation-spring

## Running this API locally

### From IntelliJ

Run as Spring Boot application with `DevApplication` as main class.

### With Maven

Use the following command to start the Spring Boot app from Maven on the command line:

    mvn -Pdev

### Local OAuth2 server

An OAuth2 server is required to run alongside the application locally. When
using either of the the above two methods to start app, an embedded mock OAuth2
server is automatically started on port `19111`, if something isn't already
listening on that port.

### Accessing the API

API is available on http://localhost:9211

It is meant to be called from app `pam-tsbx-front` on behalf of end users.

## Tests

Integration test [`MessagesControllerIT`][3] tests the API token validation
using a temporary mock OAuth2 server instance.

[3]: src/test/java/no/nav/arbeid/tsbx/messages/MessagesControllerIT.java
