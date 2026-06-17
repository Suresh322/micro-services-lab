package com.microlab.account_service.service.impl;

import com.microlab.account_service.constants.AccountConstants;
import com.microlab.account_service.dto.AccountDTO;
import com.microlab.account_service.dto.CustomerDTO;
import com.microlab.account_service.dto.ResponseDTO;
import com.microlab.account_service.entity.Account;
import com.microlab.account_service.entity.Customer;
import com.microlab.account_service.enums.AccountTypeEnum;
import com.microlab.account_service.exception.CustomerAlreadyExistsException;
import com.microlab.account_service.exception.ResourceNotFoundException;
import com.microlab.account_service.exception.ValidationException;
import com.microlab.account_service.mapper.AccountMapper;
import com.microlab.account_service.mapper.CustomerMapper;
import com.microlab.account_service.repository.AccountRepository;
import com.microlab.account_service.repository.CustomerRepository;
import com.microlab.account_service.service.AccountService;
import com.microlab.account_service.util.AccountNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

	private final CustomerRepository customerRepository;
	private final AccountRepository accountRepository;
	private final CustomerMapper customerMapper;
	private final AccountMapper accountMapper;
	private final AccountNumberGenerator accountNumberGenerator;

	@Override
	@Transactional
	public AccountDTO createAccount(CustomerDTO customerDTO) {
		LocalDateTime now = LocalDateTime.now();

		validateCustomerRequest(customerDTO);

		Customer customer = customerRepository.save(customerMapper.toNewCustomer(customerDTO, now));
		Account account = accountMapper.toNewAccount(customer, now);
		account.setAccountNumber(accountNumberGenerator.generateUniqueAccountNumber());
		account = accountRepository.save(account);

		return accountMapper.toDto(account);
	}


	@Override
	@Transactional(readOnly = true)
	public AccountDTO getAccountByMobileNumber(String mobileNumber) {
		Account account = accountRepository.findByCustomerMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Account not found for mobile number: " + mobileNumber));

		return accountMapper.toDto(account);
	}

	@Override
	@Transactional
	public AccountDTO updateAccount(AccountDTO accountDTO) {
		if (accountDTO.getAccountNumber() == null) {
			throw new ValidationException("Account number is required");
		}

		Account account = accountRepository.findById(accountDTO.getAccountNumber())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Account not found for account number: " + accountDTO.getAccountNumber()));

		validateAccountType(accountDTO.getAccountType());
		validateCustomerUpdate(account.getCustomer(), accountDTO.getCustomerDetails());

		LocalDateTime now = LocalDateTime.now();
		accountMapper.updateFromDto(accountDTO, account);
		account.setUpdatedAt(now);
		account.setUpdatedBy(AccountConstants.UPDATED_BY);

		if (accountDTO.getCustomerDetails() != null) {
			Customer customer = account.getCustomer();
			customerMapper.updateFromDto(accountDTO.getCustomerDetails(), customer);
			customer.setUpdatedAt(now);
			customer.setUpdatedBy(AccountConstants.UPDATED_BY);
			customerRepository.save(customer);
		}

		account = accountRepository.save(account);
		return accountMapper.toDto(account);
	}

	@Override
	@Transactional
	public ResponseDTO deleteAccount(Integer accountNumber) {
		if (accountNumber == null) {
			throw new ValidationException("Account number is required");
		}

		Account account = accountRepository.findById(accountNumber)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Account not found for account number: " + accountNumber));

		accountRepository.delete(account);
		return new ResponseDTO(HttpStatus.OK.value(), "Account deleted successfully");
	}

	private void validateCustomerRequest(CustomerDTO customerDTO) {

		// Validate for not null and not empty

		if (customerDTO.getEmail() == null || customerDTO.getEmail().trim().isEmpty()) {
			throw new ValidationException("Email must not be null or empty");
		}
		if (customerDTO.getMobileNumber() == null || customerDTO.getMobileNumber().trim().isEmpty()) {
			throw new ValidationException("Mobile number must not be null or empty");
		}
		if (customerDTO.getName() == null || customerDTO.getName().trim().isEmpty()) {
			throw new ValidationException("Name must not be null or empty");
		}

		// Check if email already exists
		if (customerRepository.existsByEmail(customerDTO.getEmail())) {
			throw new CustomerAlreadyExistsException(
					"Email already registered: " + customerDTO.getEmail());
		}

		// Check if mobile number already exists
		if (customerRepository.existsByMobileNumber(customerDTO.getMobileNumber())) {
			throw new CustomerAlreadyExistsException(
					"Mobile number already registered: " + customerDTO.getMobileNumber());
		}
	}

	private void validateCustomerUpdate(Customer existingCustomer, CustomerDTO customerDTO) {
		if (customerDTO == null) {
			return;
		}

		if (customerDTO.getEmail() != null
				&& customerRepository.existsByEmailAndCustomerIdNot(
						customerDTO.getEmail(), existingCustomer.getCustomerId())) {
			throw new CustomerAlreadyExistsException(
					"Email already registered: " + customerDTO.getEmail());
		}

		if (customerDTO.getMobileNumber() != null
				&& customerRepository.existsByMobileNumberAndCustomerIdNot(
						customerDTO.getMobileNumber(), existingCustomer.getCustomerId())) {
			throw new CustomerAlreadyExistsException(
					"Mobile number already registered: " + customerDTO.getMobileNumber());
		}
	}

	private void validateAccountType(String accountType) {
		if (accountType == null) {
			return;
		}

		boolean isValid = Arrays.stream(AccountTypeEnum.values())
				.anyMatch(type -> type.name().equalsIgnoreCase(accountType));

		if (!isValid) {
			throw new ValidationException("Invalid account type: " + accountType);
		}
	}

}
