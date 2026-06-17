package com.microlab.account_service.mapper;

import com.microlab.account_service.dto.CustomerDTO;
import com.microlab.account_service.entity.Customer;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

	@Mapping(target = "customerId", ignore = true)
	@Mapping(target = "accounts", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	Customer toEntity(CustomerDTO customerDTO);

	@Mapping(target = "customerId", ignore = true)
	@Mapping(target = "accounts", ignore = true)
	@Mapping(target = "createdAt", source = "createdAt")
	@Mapping(target = "createdBy", expression = "java(com.microlab.account_service.constants.AccountConstants.CREATED_BY)")
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	Customer toNewCustomer(CustomerDTO customerDTO, LocalDateTime createdAt);

	CustomerDTO toDto(Customer customer);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "customerId", ignore = true)
	@Mapping(target = "accounts", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	void updateFromDto(CustomerDTO customerDTO, @MappingTarget Customer customer);

}
