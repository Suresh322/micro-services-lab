package com.microlab.account_service.dto;

import lombok.Data;

@Data
public class AccountDTO {

	private Integer accountNumber;
	private String accountType;
	private String branchAddress;
	private CustomerDTO customerDetails;

}
