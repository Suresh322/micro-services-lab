package com.microlab.account_service.controller;

import com.microlab.account_service.dto.*;
import com.microlab.account_service.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

	private final AccountService accountService;

	@Value("${build.version}")
	private String buildVersion;

	@Autowired
	private Environment environment;

	@Autowired
	private AccountsContactInfoDto contactInfoDto;

	@PostMapping("/create")
	public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody CustomerDTO customerDTO) {
		AccountDTO accountDTO = accountService.createAccount(customerDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(accountDTO);
	}

	@GetMapping("/fetch")
	public ResponseEntity<AccountDTO> getAccountByMobileNumber(@RequestParam String mobileNumber) {
		return ResponseEntity.ok(accountService.getAccountByMobileNumber(mobileNumber));
	}

	@PutMapping("/update")
	public ResponseEntity<AccountDTO> updateAccount(@RequestBody AccountDTO accountDTO) {
		return ResponseEntity.ok(accountService.updateAccount(accountDTO));
	}

	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDTO> deleteAccount(@RequestParam Integer accountNumber) {
		return ResponseEntity.ok(accountService.deleteAccount(accountNumber));
	}

	@GetMapping("/build-info")
	public ResponseEntity<String> getBuildInfo() {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(buildVersion);
	}

	@Operation(
			summary = "Get Java version",
			description = "Get Java versions details that is installed into cards microservice"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "HTTP Status OK"
			),
			@ApiResponse(
					responseCode = "500",
					description = "HTTP Status Internal Server Error",
					content = @Content(
							schema = @Schema(implementation = ErrorResponseDTO.class)
					)
			)
	}
	)
	@GetMapping("/java-version")
	public ResponseEntity<String> getJavaVersion() {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(environment.getProperty("JAVA_HOME"));
	}

	@Operation(
			summary = "Get Contact Info",
			description = "Contact Info details that can be reached out in case of any issues"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "HTTP Status OK"
			),
			@ApiResponse(
					responseCode = "500",
					description = "HTTP Status Internal Server Error",
					content = @Content(
							schema = @Schema(implementation = ErrorResponseDTO.class)
					)
			)
	}
	)
	@GetMapping("/contact-info")
	public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(contactInfoDto);
	}

}
