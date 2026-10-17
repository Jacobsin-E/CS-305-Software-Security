# CS 305 Software Security – Artemis Financial

**Jacobsin Enriquez** | Southern New Hampshire University | CS 305: Software Security

This repository contains my **Practices for Secure Software Report** for Artemis Financial (Project Two) and the refactored code base it documents. The report covers the encryption algorithm recommendation, certificate generation, the SHA-256 checksum, the HTTPS conversion, and the secondary and functional testing of the refactored application.

| Artifact | Description |
| --- | --- |
| `CS 305 Project Two - Practices for Secure Software Report - Jacobsin Enriquez.docx` | Practices for Secure Software Report (portfolio artifact) |
| `ssl-server_student/` | Refactored Spring Boot application: SHA-256 checksum route, HTTPS configuration, and unit tests |

---

## Reflection

### Briefly summarize your client, Artemis Financial, and its software requirements. Who was the client? What issue did the company want you to address?

Artemis Financial is a consulting company that builds individualized financial plans for its customers, covering savings, retirement, investments, and insurance. The company has a public web interface and wanted to modernize its operations without exposing client data. Specifically, it asked for a file verification step in the form of a checksum so that data transferred through the web application could be checked for tampering, and for secure communication between the browser and the server.

### What did you do well when you found your client’s software security vulnerabilities? Why is it important to code securely? What value does software security add to a company’s overall well-being?

I was methodical. I worked through each area of the course’s vulnerability assessment process flow diagram instead of fixing whatever I noticed first, and I separated the vulnerabilities I introduced from the ones the application inherited. That distinction mattered: the dependency scan flagged 16 vulnerable libraries, and I was able to show that every one of them came from the original Spring Boot 2.2.4 framework rather than from my changes.

Coding securely matters because a vulnerability is far cheaper to fix during development than after an attacker finds it. For a financial company the stakes are higher than average; in 2024, a data breach in the financial industry cost an average of $6.08 million (Bonderud, 2024). Beyond the direct cost, secure software protects client trust, supports regulatory compliance, and reduces the rework that comes from patching problems in production.

### Which part of the vulnerability assessment was challenging or helpful to you?

The most challenging part was getting the Maven build to complete successfully with the OWASP Dependency-Check scan included. It took several rounds of refactoring the build configuration and re-running it: I updated the plugin version in `pom.xml`, switched the build to a newer Java runtime the plugin requires, cleared a stale lock file left by an interrupted scan, and disabled a secondary data source that now requires its own credentials. Each failed build pointed to the next issue, and working through them one at a time taught me how to read build output instead of guessing. The most helpful part was the scan report itself. It turned a long list of abstract vulnerabilities into a specific table of libraries, versions, and severity ratings, which made it clear that upgrading the framework was the real fix.

### How did you increase layers of security? In the future, what would you use to assess vulnerabilities and decide which mitigation techniques to use?

I added security in layers so that each control still holds if another one fails. I converted the application from HTTP to HTTPS on port 8443 using a self-signed RSA 2048-bit certificate in a PKCS12 keystore and restricted it to TLS 1.2. I added a SHA-256 checksum, a NIST-approved hash algorithm (National Institute of Standards and Technology [NIST], 2015), so transferred data can be verified for integrity. Finally, I kept the new route free of user input and returned only generic error messages, which keeps the attack surface small. The approach is similar to lockout/tagout on industrial equipment: no single lock is trusted on its own.

In the future, I would start with the same structured process: map the application against the vulnerability assessment areas, run an automated dependency scan, and then prioritize mitigations by severity and by whether a vulnerability is known to be actively exploited. I would also lean on the OWASP Secure Coding Practices checklist (OWASP Foundation, 2010) to choose mitigation techniques for each area.

### How did you make certain the code and software application were functional and secure? After refactoring the code, how did you check to see whether you introduced new vulnerabilities?

For functionality, I wrote three unit tests for the checksum: one confirms a known input produces the expected SHA-256 value, one confirms different inputs produce different checksums, and one confirms the output is 64 hexadecimal characters. All tests passed, and I verified the checksum independently with a separate SHA-256 tool. I then ran the application and confirmed that `https://localhost:8443/hash` returned my data string and checksum over an encrypted connection.

For security, I ran a manual code review covering syntax, logic, input handling, error handling, secrets, and authentication, and then ran the OWASP Dependency-Check static scan (OWASP Foundation, n.d.). The scan examined 49 dependencies and confirmed that my refactoring added no new third-party libraries, so it did not introduce any new vulnerable components.

### What resources, tools, or coding practices did you use that might be helpful in future assignments or tasks?

- **OWASP Dependency-Check** with an NVD API key, run through Maven in Eclipse, for software composition analysis.
- **Java keytool** for generating and exporting certificates.
- **Java’s standard cryptography libraries**, such as `MessageDigest`, instead of custom algorithms.
- **JUnit tests** to prove that security features keep working after future changes.
- The practice of **keeping secrets out of source code and screenshots**, such as passing the NVD API key at run time instead of storing it in `pom.xml`.

### Employers sometimes ask for examples of work that you have successfully completed to show your skills, knowledge, and experience. What might you show future employers from this assignment?

I would show the Practices for Secure Software Report together with the refactored code. Together they demonstrate that I can recommend and justify an encryption approach, configure HTTPS with a certificate, implement and test a cryptographic checksum, run and interpret a dependency vulnerability scan, and communicate the results clearly to a client. The troubleshooting I did to get the scan working is also a good example of how I handle real-world tooling problems.

---

## References

Bonderud, D. (2024, August 13). *Cost of a data breach 2024: Financial industry*. IBM. https://www.ibm.com/think/insights/cost-of-a-data-breach-2024-financial-industry

National Institute of Standards and Technology. (2015). *Secure hash standard (SHS)* (Federal Information Processing Standards Publication 180-4). U.S. Department of Commerce. https://doi.org/10.6028/NIST.FIPS.180-4

OWASP Foundation. (n.d.). *OWASP Dependency-Check*. https://owasp.org/www-project-dependency-check/

OWASP Foundation. (2010). *OWASP secure coding practices quick reference guide* (Version 2.0). https://owasp.org/www-project-secure-coding-practices-quick-reference-guide/
