package com.microlab.account_service.mapper;

import com.microlab.account_service.dto.AccountDTO;
import com.microlab.account_service.entity.Account;
import com.microlab.account_service.entity.Customer;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", uses = CustomerMapper.class)
public interface AccountMapper {

	@Mapping(source = "customer", target = "customerDetails")
	AccountDTO toDto(Account account);

	@Mapping(target = "accountNumber", ignore = true)
	@Mapping(target = "customer", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	Account toEntity(AccountDTO accountDTO);

	@Mapping(target = "accountNumber", ignore = true)
	@Mapping(target = "customer", source = "customer")
	@Mapping(target = "accountType", expression = "java(com.microlab.account_service.enums.AccountTypeEnum.SAVINGS.name())")
	@Mapping(target = "branchAddress", expression = "java(com.microlab.account_service.constants.AccountConstants.DEFAULT_BRANCH_ADDRESS)")
	@Mapping(target = "createdAt", source = "createdAt")
	@Mapping(target = "createdBy", expression = "java(com.microlab.account_service.constants.AccountConstants.CREATED_BY)")
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	Account toNewAccount(Customer customer, LocalDateTime createdAt);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "accountNumber", ignore = true)
	@Mapping(target = "customer", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	void updateFromDto(AccountDTO accountDTO, @MappingTarget Account account);

}
