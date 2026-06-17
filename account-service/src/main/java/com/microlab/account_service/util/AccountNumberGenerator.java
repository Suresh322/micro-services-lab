package com.microlab.account_service.util;

import com.microlab.account_service.constants.AccountConstants;
import com.microlab.account_service.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class AccountNumberGenerator {

	private static final SecureRandom SECURE_RANDOM = new SecureRandom();
	private static final int ACCOUNT_NUMBER_RANGE = AccountConstants.MAX_ACCOUNT_NUMBER
			- AccountConstants.MIN_ACCOUNT_NUMBER + 1;

	private final AccountRepository accountRepository;

	public int generateUniqueAccountNumber() {
		for (int attempt = 0; attempt < AccountConstants.MAX_ACCOUNT_NUMBER_GENERATION_ATTEMPTS; attempt++) {
			int candidate = AccountConstants.MIN_ACCOUNT_NUMBER + SECURE_RANDOM.nextInt(ACCOUNT_NUMBER_RANGE);
			if (!accountRepository.existsByAccountNumber(candidate)) {
				return candidate;
			}
		}
		throw new IllegalStateException(
				"Unable to generate a unique 9-digit account number after "
						+ AccountConstants.MAX_ACCOUNT_NUMBER_GENERATION_ATTEMPTS + " attempts");
	}

}
