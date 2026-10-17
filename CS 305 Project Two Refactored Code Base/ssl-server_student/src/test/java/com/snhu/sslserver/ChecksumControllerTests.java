package com.snhu.sslserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class ChecksumControllerTests {

	@Test
	void knownInputProducesKnownSha256() {
		assertEquals("c8dfef9923f3f7db896d0e8ce2e6cd3b4666269a9b9e2fa83051f13e2a60c09a",
				ChecksumController.checksum(
						"Hello World Check Sum! - Jacobsin Enriquez - CS305-ArtemisFinancial-7f3a9c21"));
	}

	@Test
	void differentInputProducesDifferentChecksum() {
		assertNotEquals(ChecksumController.checksum("a"), ChecksumController.checksum("b"));
	}

	@Test
	void checksumIs64HexCharacters() {
		assertEquals(64, ChecksumController.checksum("anything").length());
	}
}
