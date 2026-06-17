package com.microlab.account_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accounts", schema = "account-service")
@Getter
@Setter
@NoArgsConstructor
public class Account extends BaseEntity {

	@Id
	@Column(name = "account_number")
	private Integer accountNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@Column(name = "account_type", nullable = false, length = 100)
	private String accountType;

	@Column(name = "branch_address", nullable = false, length = 200)
	private String branchAddress;

}
