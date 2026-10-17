# Artemis Financial – Secure Checksum Service (CS 305 Project Two)

This is my refactored version of the Artemis Financial `ssl-server_student` Spring Boot project. I added three things:

1. HTTPS with a self-signed certificate
2. A `/hash` endpoint that returns a SHA-256 checksum
3. A dependency-vulnerability scan setup (OWASP Dependency-Check)

## Requirements

- Java 8 or newer to run the app (the project targets Java 1.8)
- Java 11 or newer if you want to run Dependency-Check 12.1.0
- Maven (the included `mvnw` / `mvnw.cmd` wrapper works too)

## Run it

From this folder:

```bash
./mvnw spring-boot:run
```

On Windows use `mvnw.cmd spring-boot:run`.

## View the checksum

Open this in a browser while the app is running:

```text
https://localhost:8443/hash
```

The page shows the data string (which includes my name), the algorithm (SHA-256), and the resulting checksum.

### About the "Not secure" warning

The certificate is self-signed, so the browser does not recognize the issuer and shows a warning even though the connection is encrypted. For this assignment that is expected. Click through the warning (Advanced > Proceed to localhost) to see the page. In a real deployment the certificate would come from a trusted certificate authority. I did not add the certificate to my system trust store.

## How it works

| Item | Setting |
| --- | --- |
| Port | 8443 |
| Protocol | TLS 1.2 only (the Java 8 runtime used for this project does not offer TLS 1.3) |
| Keystore | `src/main/resources/keystore.p12` (PKCS12) |
| Key alias | `artemisfinancial` |
| Certificate | RSA 2048-bit, SHA256withRSA, exported as `artemisfinancial.cer` |
| Checksum | SHA-256 via Java `MessageDigest`, shown as lowercase hex |

The SSL settings live in `src/main/resources/application.properties`. The checksum code is in `src/main/java/com/snhu/sslserver/SslServerApplication.java`.

SHA-256 replaces weaker algorithms such as MD5 and SHA-1, which have known collision weaknesses.

## Tests

```bash
./mvnw test
```

`ChecksumControllerTests` checks that a known input gives the expected SHA-256 value, that different inputs give different checksums, and that the output is 64 hex characters.

## Dependency scan

The OWASP Dependency-Check Maven plugin (version 12.1.0) is configured in `pom.xml`. To run it:

```bash
./mvnw org.owasp:dependency-check-maven:check
```

It needs Java 11 or newer and network access to download the NVD vulnerability data. On my managed laptop that download was blocked by the network proxy, so the scan results are not included here. I did not want to claim results I could not generate. Anyone with open access to Maven Central and the NVD can run the command above to produce `target/dependency-check-report.html`.

## Notes

- The keystore password is stored in `application.properties` because this is a coursework project with a throwaway certificate. A production system should load secrets from a vault or environment variables instead.
- The `target/` build folder is intentionally left out of the submission.

## What is in the submission

- `ssl-server_student/` – the refactored project (this folder)
- `CS 305 Project Two Refactored Code Base.zip` – the same project, zipped
- `CS 305 Project Two - Practices for Secure Software Report - Jacobsin Enriquez.docx` – the written report (submitted separately)
