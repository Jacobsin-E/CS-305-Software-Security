package com.snhu.sslserver;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@SpringBootApplication
public class SslServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SslServerApplication.class, args);
	}

}

@RestController
class ChecksumController {

	private static final String ALGORITHM = "SHA-256";
	private static final String DATA = "Hello World Check Sum! - Jacobsin Enriquez - CS305-ArtemisFinancial-7f3a9c21";

	@GetMapping(value = "/hash", produces = MediaType.TEXT_PLAIN_VALUE)
	public String hash() {
		return "Data: " + DATA + "\nAlgorithm: " + ALGORITHM + "\nChecksum: " + checksum(DATA);
	}

	static String checksum(String data) {
		try {
			MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
			byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
			StringBuilder hex = new StringBuilder(hash.length * 2);
			for (byte b : hash) {
				hex.append(String.format("%02x", b));
			}
			return hex.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Checksum unavailable");
		}
	}
}
