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

### Local OAuth2 server

First you need to start a plain Mock OAuth2 server listening on localhost
port 8181. If you already have this as part of running `pam-tsbx-front`, then
you do not need to do it here.

From the project directory:

    docker-compose up -d

### From IntelliJ

Run as Spring Boot application with `DevApplication` as main class.

### With Maven

Use the following command to start the Spring Boot app from Maven on the command line:

    mvn -Pdev

### Accessing the API

API is available on http://localhost:9211

It is meant to be called from app `pam-tsbx-front` on behalf of end users.
