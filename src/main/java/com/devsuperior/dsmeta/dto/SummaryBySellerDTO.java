package com.devsuperior.dsmeta.dto;

public class SummaryBySellerDTO {

	private String sellerName;
	private Double total;

	public SummaryBySellerDTO(String sellerName, Double total) {
		this.sellerName = sellerName;
		this.total = total;
	}

	public String getSellerName() {
		return sellerName;
	}

	public Double getTotal() {
		return total;
	}
}
