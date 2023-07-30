package com.devsuperior.dsmeta.dto;

import java.time.LocalDate;

public class SaleWithSellerNameDTO {

	private Long id;
	private LocalDate date;
	private Double amount;
	private String sellerName;

	public SaleWithSellerNameDTO(Long id, LocalDate date, Double amount, String sellerName) {
		this.id = id;
		this.amount = amount;
		this.date = date;
		this.sellerName = sellerName;
	}

	public Long getId() {
		return id;
	}

	public Double getAmount() {
		return amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getSellerName() { return sellerName; }
}
