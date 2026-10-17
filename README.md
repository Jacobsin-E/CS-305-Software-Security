# CS 305 Software Security – Artemis Financial

**Jacobsin Enriquez** | Southern New Hampshire University | CS 305: Software Security

This repository contains my **Practices for Secure Software Report** for Artemis Financial (Project Two) and the refactored code base it documents. The report covers the encryption algorithm recommendation, certificate generation, the SHA-256 checksum, the HTTPS conversion, and the secondary and functional testing of the refactored application.

| Artifact | Description |
| --- | --- |
| `CS305 Project Two -Practices for Secure Software Report - Jacobsin Enriquez.docx` | Practices for Secure Software Report (portfolio artifact) |
| `CS 305 Project Two Refactored Code Base/ssl-server_student/` | Refactored Spring Boot application: SHA-256 checksum route, HTTPS configuration, and unit tests |

---

## Reflection

### Briefly summarize your client, Artemis Financial, and its software requirements. Who was the client? What issue did the company want you to address?

Artemis Financial is a consulting company that builds individualized financial plans for its customers, covering savings, retirement, investments, and insurance. The company has a public web interface and wanted to modernize its operations without exposing client data. Specifically, it asked for a file verification step in the form of a checksum so that data transferred through the web application could be checked for tampering, and for secure communication between the browser and the server.

### What did you do well when you found your client’s software security vulnerabilities? Why is it important to code securely? What value does software security add to a company’s overall well-being?

I was methodical. I worked through each area of the course’s vulnerability assessment process flow diagram instead of fixing whatever I noticed first, and I separated the vulnerabilities I introduced from the ones the application inherited. That distinction mattered: the dependency scan flagged 16 vulnerable libraries, and I was able to show that every one of them came from the original Spring Boot 2.2.4 framework rather than from my changes.

Coding securely matters because a vulnerability is far cheaper to fix during development than after an attacker finds it. For a financial company the stakes are higher than average; in 2024, a data breach in the financial industry cost an average of $6.08 million (Bonderud, 2024). Beyond the direct cost, secure software protects client trust, supports regulatory compliance, and reduces the rework that comes from patching problems in production.

### Which part of the vulnerability
