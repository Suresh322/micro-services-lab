package com.microlab.account_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {

	@NotBlank
	@Size(max = 100)
	private String name;

	@NotBlank
	@Email
	@Size(max = 200)
	private String email;

	@NotBlank
	@Size(max = 20)
	private String mobileNumber;



}
