package com.microlab.account_service.service;

import com.microlab.account_service.dto.AccountDTO;
import com.microlab.account_service.dto.CustomerDTO;
import com.microlab.account_service.dto.ResponseDTO;

public interface AccountService {

	AccountDTO createAccount(CustomerDTO customerDTO);

	AccountDTO getAccountByMobileNumber(String mobileNumber);

	AccountDTO updateAccount(AccountDTO accountDTO);

	ResponseDTO deleteAccount(Integer accountNumber);

}
